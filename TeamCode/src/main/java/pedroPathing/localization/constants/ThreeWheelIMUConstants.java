package pedroPathing.localization.constants;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.localization.Encoder;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;

/**
 * This is the ThreeWheelIMUConstants class. It holds many constants and parameters for the Three Wheel + IMU Localizer.
 * @author Baron Henderson - 20077 The Indubitables
 * @version 1.0, 12/24/2024
 */

@Config
public class ThreeWheelIMUConstants {

    /** The number of inches per tick of the encoder for forward movement
     * Default Value: .001989436789 */
    public static double forwardTicksToInches = .001989436789;

    /** The number of inches per tick of the encoder for lateral movement (strafing)
     * Default Value: .001989436789 */
    public static double strafeTicksToInches = .001989436789;

    /** The number of inches per tick of the encoder for turning
     * Default Value: .001989436789 */
    public static double turnTicksToInches = .001989436789;

    /** The Y Offset of the Left Encoder (Deadwheel) from the center of the robot
     * Default Value: 1 */
    public static double leftY = 1;

    /** The Y Offset of the Right Encoder (Deadwheel) from the center of the robot
     * Default Value: -1 */
    public static double rightY = -1;

    /** The X Offset of the Strafe Encoder (Deadwheel) from the center of the robot
     * Default Value: -2.5 */
    public static double strafeX = -2.5;

    /** The Hardware Map Name of the IMU (built-in IMU will be Port 0, "imu")
     * Default Value: "imu" */
    public static String IMU_HardwareMapName = "imu";

    /** The name of the Left Encoder in the hardware map (name of the motor port it is plugged into)
     * Default Value: "leftFront" */
    public static String leftEncoder_HardwareMapName = "leftFront";

    /** The name of the Right Encoder in the hardware map (name of the motor port it is plugged into)
     * Default Value: "rightRear" */
    public static String rightEncoder_HardwareMapName = "rightRear";

    /** The name of the Strafe Encoder in the hardware map (name of the motor port it is plugged into)
     * Default Value:Default Value: "rightFront" */
    public static String strafeEncoder_HardwareMapName = "rightFront";

    /** The Orientation of the Control Hub (for IMU) on the Robot
     * Default Value: new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP, RevHubOrientationOnRobot.UsbFacingDirection.LEFT) */
    public static RevHubOrientationOnRobot IMU_Orientation = new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP, RevHubOrientationOnRobot.UsbFacingDirection.LEFT);

    /** The direction of the Left Encoder
     * Default Value: Encoder.REVERSE */
    public static double leftEncoderDirection = Encoder.REVERSE;

    /** The direction of the Right Encoder
     * Default Value: Encoder.FORWARD */
    public static double rightEncoderDirection = Encoder.REVERSE;

    /** The direction of the Strafe Encoder
     * Default Value: Encoder.FORWARD */
    public static double strafeEncoderDirection = Encoder.FORWARD;

}
