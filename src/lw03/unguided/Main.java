

public class Main {
    public static void main(String[] args) {
        java.util.Set<String> registeredStudents = new java.util.HashSet<>();
        java.util.List<String> checkins = new java.util.ArrayList<>();
        java.util.Map<String, Boolean> attendance = new java.util.HashMap<>();

        int successfulCheckins = 0;
        int rejectedAttempts = 0;

        try {
            java.util.Scanner registrationScanner =
                    new java.util.Scanner(new java.io.File("registrations.txt"));

            while (registrationScanner.hasNextLine()) {
                String studentId = registrationScanner.nextLine();
                registeredStudents.add(studentId);
                attendance.put(studentId, false);
            }

            registrationScanner.close();

            java.util.Scanner checkinScanner =
                    new java.util.Scanner(new java.io.File("checkins.txt"));

            while (checkinScanner.hasNextLine()) {
                checkins.add(checkinScanner.nextLine());
            }

            checkinScanner.close();

            System.out.println("===== Event Check-In Results =====");

            for (int i = 0; i < checkins.size(); i++) {
                String studentId = checkins.get(i);

                if (!registeredStudents.contains(studentId)) {
                    System.out.println(
                            studentId + ": Rejected (not registered)"
                    );
                    rejectedAttempts++;
                } else if (attendance.get(studentId)) {
                    System.out.println(
                            studentId + ": Rejected (already checked in)"
                    );
                    rejectedAttempts++;
                } else {
                    attendance.put(studentId, true);
                    successfulCheckins++;
                    System.out.println(studentId + ": Checked in");
                }
            }

            int absentStudents =
                    registeredStudents.size() - successfulCheckins;

            System.out.println();
            System.out.println("===== Final Event Summary =====");
            System.out.println(
                    "Registered students: " + registeredStudents.size()
            );
            System.out.println(
                    "Successful check-ins: " + successfulCheckins
            );
            System.out.println("Absent students: " + absentStudents);
            System.out.println("Rejected attempts: " + rejectedAttempts);

        } catch (java.io.FileNotFoundException e) {
            System.out.println(
                    "File registrations.txt atau checkins.txt tidak ditemukan."
            );
        }
    }
}


