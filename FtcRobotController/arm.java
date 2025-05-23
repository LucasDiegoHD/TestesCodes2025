package org.firstinspires.ftc.teamcode.opmode;

import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.constants.Constants;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@TeleOp
public class PedroFullTeleOp extends OpMode {

    // --- Drive (PedroPathing) ---
    private Follower follower;
    private final Pose startPose = new Pose(0, 0, 0);

    // --- Gripper subsystems ---
    private MotorEx elevatorMotor;
    private Servo leftArmServo, rightArmServo, clawServo;

    // Servo positions (ajuste conforme necessário)
    private static final double ARM_UP_POS      = 0.2;
    private static final double ARM_DOWN_POS    = 0.8;
    private static final double CLAW_OPEN_POS   = 0.3;
    private static final double CLAW_CLOSED_POS = 0.7;

    // Debounce para o botão A no drive
    private boolean aPressedLast = false;

    @Override
    public void init() {
        // 1) Configura as constantes do PedroPathing
        Constants.setConstants(FConstants.class, LConstants.class);

        // 2) Inicializa o Follower
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startPose);

        // 3) Inicializa o elevador
        elevatorMotor = new MotorEx(hardwareMap, "elevatorMotor");
        elevatorMotor.setRunMode(MotorEx.RunMode.RawPower);
        elevatorMotor.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);

        // 4) Inicializa os servos do braço
        leftArmServo  = hardwareMap.get(Servo.class, "leftArmServo");
        rightArmServo = hardwareMap.get(Servo.class, "rightArmServo");
        rightArmServo.setDirection(Servo.Direction.REVERSE);

        // 5) Inicializa o servo da garra
        clawServo = hardwareMap.get(Servo.class, "clawServo");
    }

    @Override
    public void start() {
        // Entra em modo teleop-drive do PedroPathing
        follower.startTeleopDrive();
    }

    @Override
    public void loop() {
        // —— 1) DRIVE FIELD-CENTRIC ——
        follower.setTeleOpMovementVectors(
                -gamepad1.left_stick_y,   // forward/back
                -gamepad1.left_stick_x,   // strafe
                -gamepad1.right_stick_x,  // rotate
                false                     // robot-centric? false = field-centric
        );
        follower.update();

        // —— 2) ELEVADOR ——
        if (gamepad1.right_trigger > 0.1) {
            elevatorMotor.set(1.0);
        } else if (gamepad1.right_bumper) {
            elevatorMotor.set(-1.0);
        } else {
            elevatorMotor.set(0.0);
        }

        // —— 3) BRAÇO DA GARRA ——
        if (gamepad1.y) {
            leftArmServo.setPosition(ARM_UP_POS);
            rightArmServo.setPosition(ARM_UP_POS);
        } else if (gamepad1.a && !aPressedLast) {
            // exemplo de ação extra no A (se quiser)
        }
        aPressedLast = gamepad1.a;

        // —— 4) ABRIR / FECHAR GARRA ——
        if (gamepad1.x) {
            clawServo.setPosition(CLAW_CLOSED_POS);
        } else if (gamepad1.b) {
            clawServo.setPosition(CLAW_OPEN_POS);
        }

        // —— 5) TELEMETRIA ——
        telemetry.addData("Drive X", follower.getPose().getX());
        telemetry.addData("Drive Y", follower.getPose().getY());
        telemetry.addData("Heading (°)", Math.toDegrees(follower.getPose().getHeading()));
        telemetry.addData("Elev Pos (ticks)", elevatorMotor.getCurrentPosition());
        telemetry.update();
    }

    @Override
    public void stop() {
        // Para tudo ao final
        follower.stop();
        elevatorMotor.set(0);
    }
}
