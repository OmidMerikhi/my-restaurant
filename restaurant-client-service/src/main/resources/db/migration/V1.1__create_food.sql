create table food
(
    id       varchar(200)                                                                                              not null primary key,
    title    varchar(200)                                                                                              null,
    material varchar(200)                                                                                              null,
    price    double                                                                                                    null,
    size     varchar(200)                                                                                              null,
    active   boolean default true                                                                                      null,
    category enum ('PIZZA', 'WARM_SANDWICH', 'COLD_SANDWICH', 'SPECIAL', 'CHICKEN', 'DONER_KABAB', 'DRINK', 'STARTER') null
);

