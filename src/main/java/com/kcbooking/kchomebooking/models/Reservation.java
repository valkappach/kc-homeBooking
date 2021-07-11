package com.kcbooking.kchomebooking.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Reservation {
    public static final  double TVA = 1;
    //public static final double TAX_AMOUNT = 0.10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID reservationId= UUID.randomUUID();
    @OneToOne(mappedBy = "reservation")
    private Room room;

    @Embedded
    @Valid
    private ReservationDates dates = new ReservationDates();

//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinTable(
//            name = "reservation_guests",
//            joinColumns = @JoinColumn(name = "reservation_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "guest_id", referencedColumnName = "id")
//    )
    @ManyToMany(fetch = FetchType.EAGER)
    //private Collection<Guest> guests=new ArrayList<>();
    private Set<Guest> guests = new HashSet<>();

//    @ManyToMany
//    @JoinTable(
//            name = "reservation_general_extras",
//            joinColumns = @JoinColumn(name = "reservation_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "general_extra_id", referencedColumnName = "id")
//    )
@ManyToMany(fetch = FetchType.EAGER)
    //private Collection<Extra> generalExtras=new ArrayList<>();
    private Set<Extra> generalExtras = new HashSet<>();


    /**
     * Add a guest only if the room has free beds.
     *
     * @param guest The guest to add.
     */
    public void addGuest(Guest guest) {
        if (!isRoomFull()) {
            guests.add(guest);
        }
    }

    public void clearGuests() {
        guests.clear();
    }

    /**
     * Allows UI to easily remove a {@code Guest} in the 'add guest' page. Its easier for the UI to 'POST' a guest id
     * rather than provide the full {@code Guest} details that match {@code Guest.equals/hashCode}.
     *
     * @param guestId The temporarily assigned guest id.
     * @return {@code true} if the {@code Guest} was removed otherwise {@code false}.
     */
    public boolean removeGuestById(UUID guestId) {
        return guests.removeIf(guest -> guest.getTempId().equals(guestId));
    }

    public UUID getReservationId() {
        return reservationId;
    }

    public Set<Extra> getGeneralExtras() {
        return generalExtras;
    }

    /**
     * All {@code Extra}s must be {@code Extra.Category.General} otherwise an IllegalArgumentException is thrown.
     *
     * @param generalExtras All {@code Extra}s must be {@code Extra.Category.General}.
     * @throws IllegalArgumentException
     */
    public void setGeneralExtras(Set<Extra> generalExtras) throws IllegalArgumentException {
        boolean containsInvalidCategories
                = generalExtras.stream().anyMatch(extra -> extra.getCategory() != Extra.Category.General);
        if (containsInvalidCategories) {
            throw new IllegalArgumentException("Contains invalid categories that are not Extra.Category.General");
        }
        this.generalExtras = generalExtras;
    }
//
//    public List<MealPlan> getMealPlans() {
//        return mealPlans;
//    }
//
//    public void setMealPlans(List<MealPlan> mealPlans) {
//        this.mealPlans = mealPlans;
//    }

    public ReservationDates getDates() {
        return dates;
    }

    public void setDates(ReservationDates dates) {
        this.dates = dates;
    }

    public boolean isRoomFull() {
        return guests.size() >= room.getBeds();
    }

    public boolean hasGuests() {
        return !guests.isEmpty();
    }

    public boolean hasAtLeastOneAdultGuest() {
        return guests.stream().anyMatch(guest -> !guest.isChild());
    }

    /**
     * Calculates {@code Extra.Type} to correctly charge the food and general extras.
     *
     * @return Depending on the room type, return {@code Extra.Type.Premium/Basic}.
     */
    public Extra.Type getExtraPricingType() {
        switch (room.getRoomType()) {
            case Luxury:
            case Business:
                return Extra.Type.Premium;
            default:
                return Extra.Type.Basic;
        }
    }

    /**
     * Calculates the chargeable late fee price only if the user has selected the late checkout option.
     */
    public BigDecimal getChargeableLateCheckoutFee() {
        return dates.isLateCheckout() ? getLateCheckoutFee() : BigDecimal.ZERO;
    }

    /**
     * The late checkout fee depending on the type of room.
     * For the actual chargeable fee, use {@link #getChargeableLateCheckoutFee()}
     */
    public BigDecimal getLateCheckoutFee() {
        switch (room.getRoomType()) {
            case Luxury:
            case Business:
                return BigDecimal.ZERO;
            default:
                return room.getHotel().getLateCheckOutFee();
        }
    }

    /**
     * No late fee is considered.
     * Provided separately to allow break down to sub totals on invoices.
     *
     * @return Total nights * per night cost
     */
    public BigDecimal getTotalRoomCost() {
        long nights = dates.totalNights();
        if (nights == 0) {
            return BigDecimal.ZERO;
        }
        return room.getPriceNight().multiply(BigDecimal.valueOf(nights));
    }

    /**
     * Provided separately to allow break down to sub totals on invoices.
     *
     * @return {@link #getTotalRoomCost} + {@link #getChargeableLateCheckoutFee}
     */
    public BigDecimal getTotalRoomCostWithLateCheckoutFee() {
        return getTotalRoomCost().add(getChargeableLateCheckoutFee());
    }

    /**
     * Calculates the total general extras cost.
     * Provided separately to allow break down to sub totals on invoices.
     * <p>
     * {@code Daily extra cost * total nights}
     */
    public BigDecimal getTotalGeneralExtrasCost() {
        long totalNights = dates.totalNights();
        return generalExtras.stream().reduce(
                BigDecimal.ZERO,
                (acc, next) -> acc.add(next.getTotalPrice(totalNights))
                , BigDecimal::add
        );
    }

//    /**
//     * Provided separately to allow break down to sub totals on invoices.
//     *
//     * @return Total cost of all guests meal plans
//     */
//    public BigDecimal getTotalMealPlansCost() {
//        return mealPlans.stream()
//                .map(MealPlan::getTotalMealPlanCost)
//                .reduce(BigDecimal.ZERO, BigDecimal::add, BigDecimal::add);
//    }

    /**
     * Total cost including everything!
     * Provided separately to allow break down to sub totals on invoices.
     */
    public BigDecimal getTotalCostExcludingTax() {
        return getTotalRoomCostWithLateCheckoutFee()
                .add(getTotalGeneralExtrasCost());
//                .add(getTotalMealPlansCost());
    }

    /**
     * Provided separately to allow break down to sub totals on invoices.
     *
     * @return The taxable amount from the total cost. Eg 10% of $100 = $10.
     */
    public BigDecimal getTaxableAmount() {
        return getTotalCostExcludingTax().multiply(BigDecimal.valueOf(TVA));
    }

    /**
     * Provided separately to allow break down to sub totals on invoices.
     *
     * @return The total cost including tax.
     */
    public BigDecimal getTotalCostIncludingTax() {
        return getTotalCostExcludingTax().add(getTaxableAmount());
    }


//    /**
//     * Creates a new {@code MealPlan} for each {@code Guest} and assigns the result to the internal {@code mealPlans}
//     * instance variable.
//     *
//     * <p>The reason this modifies internal state is to ensure all the {@code MealPlan}s are setup in sorted
//     * order ready to be binded to dynamic fields in thymeleaf template.</p>
//     */
//    public void createMealPlans() {
//        mealPlans = guests.stream()
//                .map(guest -> new MealPlan(guest, this))
//                .sorted(Comparator.comparing(MealPlan::getGuest, Guest.comparator()))
//                .collect(Collectors.toList());
//    }

//    /**
//     * Since {@link #createMealPlans} creates a meal plan for each {@code Guest}. Simply checking the list size is
//     * not going to tell you if the meal plans are empty. Instead empty is defined by the total being zero dollars.
//     *
//     * @return {@code true} if all meal plans add up to 0.
//     */
//    public boolean hasEmptyMealPlans() {
//        return mealPlans.stream()
//                .map(MealPlan::getTotalMealPlanCost)
//                .reduce(BigDecimal.ZERO, BigDecimal::add, BigDecimal::add)
//                .equals(BigDecimal.ZERO);
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(reservationId, that.reservationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationId);
    }


}
