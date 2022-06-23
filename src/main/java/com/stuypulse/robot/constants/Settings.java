/************************ PROJECT DORCAS ************************/
/* Copyright (c) 2022 StuyPulse Robotics. All rights reserved.  */
/* This work is licensed under the terms of the MIT license.    */
/****************************************************************/

package com.stuypulse.robot.constants;

import com.stuypulse.stuylib.control.PIDController;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N2;
import edu.wpi.first.math.system.LinearSystem;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;

/*-
 * File containing tunable settings for every subsystem on the robot.
 *
 * We use StuyLib's SmartNumber / SmartBoolean in order to have tunable
 * values that we can edit on Shuffleboard.
 */
public final class Settings {
	public static double DT = 0.02;

	public interface Elevator {
		double MOTOR_RADIUS = Units.inchesToMeters(1);
		
		double MIN_HEIGHT = Units.inchesToMeters(5);
		double MAX_HEIGHT = Units.inchesToMeters(90);

		double MASS = Units.lbsToKilograms(50);

		public interface Feedforward {
			double kG = 1.3;
			double kS = 0.01;
			double kV = 0.5;
			double kA = 0.1;

			public static ElevatorFeedforward getFeedforward() {
				return new ElevatorFeedforward(kS, kG, kV, kA);
			}
		}

		public interface Feedback {
			double VEL_kP = 1;
			double VEL_kI = 0;
			double VEL_kD = 0.2;

			double kP = 1;
			double kI = 0;
			double kD = 0.2;

			public static PIDController getVelFeedback() {
				return new PIDController(VEL_kP, VEL_kI, VEL_kD);
			}

			public static PIDController getFeedback() {
				return new PIDController(kP, kI, kD);
			}
		}

		public interface System {
			double GEARING = 0.01;

			double ENCODER_MULTIPLIER = 6.175038019510E-5 * 0.0254;

			double MAX_ACCELERATION = 1;
			double MAX_VELOCITY = 1.5;

			public static LinearSystem<N2, N1, N1> getSystem() {
				return LinearSystemId.createElevatorSystem(
					DCMotor.getNeo550(1),
					MASS,
					MOTOR_RADIUS,
					GEARING
				);
			}

			public static ElevatorSim getSim() {
				return new ElevatorSim(
					getSystem(),
					DCMotor.getNeo550(1),
					GEARING,
					MOTOR_RADIUS,
					MIN_HEIGHT,
					MAX_HEIGHT
				);
			}
		}
	}
}
