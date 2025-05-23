package pedroPathing.pathgen;

import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.Point;
import com.pedropathing.pathgen.Vector;

/**
 * This is the MathFunctions class. This contains many useful math related methods that I use in
 * other classes to simplify code elsewhere.
 *
 * @author Anyi Lin - 10158 Scott's Bots
 * @author Aaron Yang - 10158 Scott's Bots
 * @author Harrison Womack - 10158 Scott's Bots
 * @version 1.0, 3/9/2024
 */
public class MathFunctions {

    /**
     * This is a simple implementation of the choose function in math. It's equivalent to the number
     * of ways you can choose r items from n total items, if only which items got picked and not the
     * order of picking the items mattered.
     *
     * @param n this is how many you want to choose from.
     * @param r this is how many you want to choose.
     * @return returns the result of the "n choose r" function.
     */
    public static double nCr(int n, int r) {
        double num = 1;
        double denom = 1;

        // this multiplies up the numerator of the nCr function
        for (int i = n; i > n - r; i--) {
            num *= i;
        }

        // this multiplies up the denominator of the nCr function
        for (int i = 1; i <= r; i++) {
            denom *= i;
        }

        return num / denom;
    }

    /**
     * This returns the sign (positive/negative) of a number.
     *
     * @param get the number.
     * @return returns the sign of the number.
     */
    public static double getSign(double get) {
        if (get == 0) return 0;
        if (get > 0) return 1;
        return -1;
    }

    /**
     * This clamps down a value to between the lower and upper bounds inclusive.
     *
     * @param num the number to be clamped.
     * @param lower the lower bound.
     * @param upper the upper bound.
     * @return returns the clamped number.
     */
    public static double clamp(double num, double lower, double upper) {
        if (num < lower) return lower;
        if (num > upper) return upper;
        return num;
    }

    /**
     * This normalizes an angle to be between 0 and 2 pi radians, inclusive.
     * <p>
     * IMPORTANT NOTE: This method operates in radians.
     *
     * @param angleRadians the angle to be normalized.
     * @return returns the normalized angle.
     */
    public static double normalizeAngle(double angleRadians) {
        double angle = angleRadians % (2*Math.PI);
        if (angle < 0) {
            return angle + 2*Math.PI;
        }
        return angle;
    }

    /**
     * This returns the smallest angle between two angles. This operates in radians.
     *
     * @param one one of the angles.
     * @param two the other one.
     * @return returns the smallest angle.
     */
    public static double getSmallestAngleDifference(double one, double two) {
        return Math.min(MathFunctions.normalizeAngle(one - two), MathFunctions.normalizeAngle(two - one));
    }

    /**
     * This gets the direction to turn between a start heading and an end heading. Positive is left
     * and negative is right. This operates in radians.
     *
     * @return returns the turn direction.
     */
    public static double getTurnDirection(double startHeading, double endHeading) {
        if (MathFunctions.normalizeAngle(endHeading - startHeading) >= 0 && MathFunctions.normalizeAngle(endHeading - startHeading) <= Math.PI) {
            return 1; // counter clock wise
        }
        return -1; // clock wise
    }

    /**
     * This returns the distance between a Pose and a Point,
     *
     * @param pose this is the Pose.
     * @param point this is the Point.
     * @return returns the distance between the two.
     */
    public static double distance(Pose pose, com.pedropathing.pathgen.Point point) {
        return Math.sqrt(Math.pow(pose.getX() - point.getX(), 2) + Math.pow(pose.getY() - point.getY(), 2));
    }

    /**
     * This returns the distance between a Pose and another Pose.
     *
     * @param one this is the first Pose.
     * @param two this is the second Pose.
     * @return returns the distance between the two.
     */
    public static double distance(Pose one, Pose two) {
        return Math.sqrt(Math.pow(one.getX() - two.getX(), 2) + Math.pow(one.getY() - two.getY(), 2));
    }

    /**
     * This returns a Point that is the sum of the two input Point.
     *
     * @param one the first Point
     * @param two the second Point
     * @return returns the sum of the two Points.
     */
    public static com.pedropathing.pathgen.Point addPoints(com.pedropathing.pathgen.Point one, com.pedropathing.pathgen.Point two) {
        return new com.pedropathing.pathgen.Point(one.getX() + two.getX(), one.getY() + two.getY(), com.pedropathing.pathgen.Point.CARTESIAN);
    }

    /**
     * This returns a Pose that is the sum of the two input Pose.
     *
     * @param one the first Pose
     * @param two the second Pose
     * @return returns the sum of the two Pose.
     */
    public static Pose addPoses(Pose one, Pose two) {
        return new Pose(one.getX() + two.getX(), one.getY() + two.getY(), one.getHeading() + two.getHeading());
    }

    /**
     * This subtracts the second Point from the first Point and returns the result as a Point.
     * Do note that order matters here.
     *
     * @param one the first Point.
     * @param two the second Point.
     * @return returns the difference of the two Points.
     */
    public static com.pedropathing.pathgen.Point subtractPoints(com.pedropathing.pathgen.Point one, com.pedropathing.pathgen.Point two) {
        return new com.pedropathing.pathgen.Point(one.getX() - two.getX(), one.getY() - two.getY(), com.pedropathing.pathgen.Point.CARTESIAN);
    }

    /**
     * This subtracts the second Pose from the first Pose and returns the result as a Pose.
     * Do note that order matters here.
     *
     * @param one the first Pose.
     * @param two the second Pose.
     * @return returns the difference of the two Pose.
     */
    public static Pose subtractPoses(Pose one, Pose two) {
        return new Pose(one.getX() - two.getX(), one.getY() - two.getY(), one.getHeading() - two.getHeading());
    }

    /**
     * This rotates the given pose by the given theta,
     *
     * @param pose the Pose to rotate.
     * @param theta the angle to rotate by.
     * @param rotateHeading whether to adjust the Pose heading too.
     * @return the rotated Pose.
     */
    public static Pose rotatePose(Pose pose, double theta, boolean rotateHeading) {
        double x = pose.getX() * Math.cos(theta) - pose.getY() * Math.sin(theta);
        double y = pose.getX() * Math.sin(theta) + pose.getY() * Math.cos(theta);
        double heading = rotateHeading ? normalizeAngle(pose.getHeading() + theta) : pose.getHeading();

        return new Pose(x, y, heading);
    }

    /**
     * This multiplies a Point by a scalar and returns the result as a Point
     *
     * @param point the Point being multiplied.
     * @param scalar the scalar multiplying into the Point.
     * @return returns the scaled Point.
     */
    public static com.pedropathing.pathgen.Point scalarMultiplyPoint(com.pedropathing.pathgen.Point point, double scalar) {
        return new com.pedropathing.pathgen.Point(point.getX() * scalar, point.getY() * scalar, com.pedropathing.pathgen.Point.CARTESIAN);
    }

    /**
     * Copies a Point, but with a different reference location in the memory. So basically a deep
     * copy.
     *
     * @param point the Point to be deep copied.
     * @return returns the copied Point.
     */
    public static com.pedropathing.pathgen.Point copyPoint(com.pedropathing.pathgen.Point point) {
        return new com.pedropathing.pathgen.Point(point.getX(), point.getY(), Point.CARTESIAN);
    }

    /**
     * Copies a Vector, but with a different reference location in the memory. So basically a deep
     * copy.
     *
     * @param vector Vector to be deep copied.
     * @return returns the copied Vector.
     */
    public static com.pedropathing.pathgen.Vector copyVector(com.pedropathing.pathgen.Vector vector) {
        return new com.pedropathing.pathgen.Vector(vector.getMagnitude(), vector.getTheta());
    }

    /**
     * This multiplies a Vector by a scalar and returns the result as a Vector.
     *
     * @param vector the Vector being multiplied.
     * @param scalar the scalar multiplying into the Vector.
     * @return returns the scaled Vector.
     */
    public static com.pedropathing.pathgen.Vector scalarMultiplyVector(com.pedropathing.pathgen.Vector vector, double scalar) {
        return new com.pedropathing.pathgen.Vector(vector.getMagnitude() * scalar, vector.getTheta());
    }

    /**
     * This normalizes a Vector to be of magnitude 1, unless the Vector is the zero Vector.
     * In that case, it just returns back the zero Vector but with a different memory location.
     *
     * @param vector the Vector being normalized.
     * @return returns the normalized (or zero) Vector.
     */
    public static com.pedropathing.pathgen.Vector normalizeVector(com.pedropathing.pathgen.Vector vector) {
        if (vector.getMagnitude() == 0) {
            return new com.pedropathing.pathgen.Vector(0.0, vector.getTheta());
        } else {
            return new com.pedropathing.pathgen.Vector(vector.getMagnitude() / Math.abs(vector.getMagnitude()), vector.getTheta());
        }
    }

    /**
     * This returns a Vector that is the sum of the two input Vectors.
     *
     * @param one the first Vector.
     * @param two the second Vector.
     * @return returns the sum of the Vectors.
     */
    public static com.pedropathing.pathgen.Vector addVectors(com.pedropathing.pathgen.Vector one, com.pedropathing.pathgen.Vector two) {
        com.pedropathing.pathgen.Vector returnVector = new com.pedropathing.pathgen.Vector();
        returnVector.setOrthogonalComponents(one.getXComponent() + two.getXComponent(), one.getYComponent() + two.getYComponent());
        return returnVector;
    }

    /**
     * This subtracts the second Vector from the first Vector and returns the result as a Vector.
     * Do note that order matters here.
     *
     * @param one the first Vector.
     * @param two the second Vector.
     * @return returns the second Vector subtracted from the first Vector.
     */
    public static com.pedropathing.pathgen.Vector subtractVectors(com.pedropathing.pathgen.Vector one, com.pedropathing.pathgen.Vector two) {
        com.pedropathing.pathgen.Vector returnVector = new com.pedropathing.pathgen.Vector();
        returnVector.setOrthogonalComponents(one.getXComponent() - two.getXComponent(), one.getYComponent() - two.getYComponent());
        return returnVector;
    }

    /**
     * This computes the dot product of the two Vectors.
     *
     * @param one the first Vector.
     * @param two the second Vector.
     * @return returns the dot product of the two Vectors.
     */
    public static double dotProduct(com.pedropathing.pathgen.Vector one, com.pedropathing.pathgen.Vector two) {
        return one.getXComponent() * two.getXComponent() + one.getYComponent() * two.getYComponent();
    }

    /**
     * This computes the first Vector crossed with the second Vector, so a cross product.
     * Do note that order matters here.
     *
     * @param one the first Vector.
     * @param two the second Vector.
     * @return returns the cross product of the two Vectors.
     */
    public static double crossProduct(com.pedropathing.pathgen.Vector one, Vector two) {
        return one.getXComponent() * two.getYComponent() - one.getYComponent() * two.getXComponent();
    }

    /**
     * This returns whether a specified value is within a second specified value by plus/minus a
     * specified accuracy amount.
     *
     * @param one first number specified.
     * @param two Second number specified.
     * @param accuracy the level of accuracy specified.
     * @return returns if the two numbers are within the specified accuracy of each other.
     */
    public static boolean roughlyEquals(double one, double two, double accuracy) {
        return (one < two + accuracy && one > two - accuracy);
    }

    /**
     * This returns whether a specified number is within a second specified number by plus/minus 0.0001.
     *
     * @param one first number specified.
     * @param two second number specified.
     * @return returns if a specified number is within plus/minus 0.0001 of the second specified number.
     */
    public static boolean roughlyEquals(double one, double two) {
        return roughlyEquals(one, two, 0.0001);
    }

    public static double inToMM(double in) {
        return in * 25.4;
    }

    public static double mmToIn(double mm) {
        return mm / 25.4;
    }
}
