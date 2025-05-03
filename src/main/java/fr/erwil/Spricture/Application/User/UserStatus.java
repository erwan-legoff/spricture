package fr.erwil.Spricture.Application.User;

import lombok.Getter;

@Getter
public enum UserStatus {

    BLOCKED_BY_ADMIN(10),
    CREATED(20),
    EMAIL_VALIDATED(30),
    VALIDATED_BY_ADMIN(40);

    private final int rank;

    UserStatus(int rank) {
        this.rank = rank;
    }


    public boolean canLogin() {
        return this.rank >= VALIDATED_BY_ADMIN.getRank();
    }

    public boolean emailHasBeenValidated() {
        return this.rank >= EMAIL_VALIDATED.getRank();
    }

    public boolean isBlocked() {
        return this == BLOCKED_BY_ADMIN;
    }

    public boolean isBefore(UserStatus other) {
        return this.rank < other.getRank();
    }

    public boolean isAfter(UserStatus other) {
        return this.rank > other.getRank();
    }
}

