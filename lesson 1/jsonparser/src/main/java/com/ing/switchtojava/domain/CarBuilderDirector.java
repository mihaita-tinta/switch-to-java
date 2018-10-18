package com.ing.switchtojava.domain;

public class CarBuilderDirector {

    private CarBuilder builder;

    public CarBuilderDirector(final CarBuilder builder) {
        this.builder = builder;
    }

    public Car construct(Long id, String number, int seats) {
        return builder.setId(id)
                      .setNumber(number)
                      .setSeats(seats)
                      .build();
    }

    public static void main(String[] args)
    {
        CarBuilder builder = new CarBuilderImpl();

        CarBuilderDirector carBuilderDirector = new CarBuilderDirector(builder);

        System.out.println(carBuilderDirector.construct(13L, "B-999-ZEU", 2));
    }


}
