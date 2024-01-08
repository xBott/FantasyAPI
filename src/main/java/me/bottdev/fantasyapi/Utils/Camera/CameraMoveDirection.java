package me.bottdev.fantasyapi.Utils.Camera;

public enum CameraMoveDirection {
    LEFT(-12.0f, 0,  0),
    RIGHT(12.0f, 0,  0),
    UP(0, 7.0f,  0),
    DOWN(0, -7.0f, 0),
    DIAGONAL_RIGHT_LEFT_UP(-7.0f, 5.0f,  1),
    DIAGONAL_LEFT_RIGHT_UP(7.0f, 5.0f,  1),
    DIAGONAL_RIGHT_LEFT_DOWN(-7.0f, -5.0f,1),
    DIAGONAL_LEFT_RIGHT_DOWN(7.0f, -5.0f, 1);

    private final float yaw_delta;
    private final float pitch_delta;

    private final int priority;


    CameraMoveDirection(float yaw_delta, float pitch_delta, int priority) {
        this.yaw_delta = yaw_delta;
        this.pitch_delta = pitch_delta;
        this.priority = priority;
    }

    public float getDeltaYaw() {
        return yaw_delta;
    }

    public float getDeltaPitch() {
        return pitch_delta;
    }

    public int getPriority() {
        return priority;
    }
}
