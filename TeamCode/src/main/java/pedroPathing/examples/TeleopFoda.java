package pedroPathing.examples;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathSegment;
import com.pedropathing.constants.Constants;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@TeleOp
public class ExampleFieldCentricTeleop extends OpMode {
    private Follower follower;
    private final Pose startPose = new Pose(0, 0, 0);

    @Override
    public void init() {
        // Configura as constantes antes de criar o follower
        Constants.setConstants(FConstants.class, LConstants.class);

        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startPose);
    }

    @Override
    public void init_loop() {
        // Nenhuma ação necessária aqui
    }

    @Override
    public void start() {
        follower.startTeleopDrive();
    }

    @Override
    public void loop() {
        // Controle mecanum com controle de gamepad (field-centric)
        follower.setTeleOpMovementVectors(
                -gamepad1.left_stick_y,
                -gamepad1.left_stick_x,
                -gamepad1.right_stick_x,
                false
        );
        follower.update();

        // Se apertar A, vai para posição fixa
        if (gamepad1.a) {
            goToPose(new Pose(30, 30, 0));  // x=30cm, y=30cm, heading=0 radianos
        }

        telemetry.addData("X", follower.getPose().getX());
        telemetry.addData("Y", follower.getPose().getY());
        telemetry.addData("Heading (deg)", Math.toDegrees(follower.getPose().getHeading()));
        telemetry.update();
    }

    private void goToPose(Pose targetPose) {
        Path path = new Path(follower.getPose());  // Começa da posição atual do robô
        path.addSegment(new PathSegment(targetPose));  // Adiciona um segmento até a pose alvo
        follower.followPath(path);
    }

    @Override
    public void stop() {
        // Pode colocar código para parar o robô, se quiser
    }
}
