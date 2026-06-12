package clinic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents a clinic with patients and doctors.
 */
public class Clinic {

    private Map<String, Patient> patients = new HashMap<>();
    private Map<Integer, Doctor> doctors = new HashMap<>();

    /**
     * Add a new clinic patient.
     */
    public void addPatient(String first, String last, String ssn) {
        Patient p = new Patient(first, last, ssn);
        patients.put(ssn, p);
    }

    /**
     * Retrieves a patient information.
     */
    public String getPatient(String ssn) throws NoSuchPatient {
        if(!patients.containsKey(ssn)) {
            throw new NoSuchPatient();
        }
        return patients.get(ssn).toString();
    }

    /**
     * Add a new doctor working at the clinic.
     */
    public void addDoctor(String first, String last, String ssn, int docID, String specialization) {
        Doctor d = new Doctor(first, last, ssn, docID, specialization);
        patients.put(ssn, d);
        doctors.put(docID, d);
    }

    /**
     * Retrieves information about a doctor.
     */
    public String getDoctor(int docID) throws NoSuchDoctor {
        if(!doctors.containsKey(docID)) {
            throw new NoSuchDoctor();
        }
        return doctors.get(docID).toString();
    }

    /**
     * Assign a given doctor to a patient.
     */
    public void assignPatientToDoctor(String ssn, int docID) throws NoSuchPatient, NoSuchDoctor {
        if(!patients.containsKey(ssn)) {
            throw new NoSuchPatient();
        }
        if(!doctors.containsKey(docID)) {
            throw new NoSuchDoctor();
        }

        Patient patient = patients.get(ssn);
        Doctor oldDoctor = patient.getDoctor();
        Doctor newDoctor = doctors.get(docID);

        if(oldDoctor != null && oldDoctor != newDoctor) {
            oldDoctor.removePatient(patient);
        }

        patient.setDoctor(newDoctor);
        newDoctor.addPatient(patient);
    }

    /**
     * Retrieves the id of the doctor assigned to a given patient.
     */
    public int getAssignedDoctor(String ssn) throws NoSuchPatient, NoSuchDoctor {
        if(!patients.containsKey(ssn)) {
            throw new NoSuchPatient();
        }

        Doctor doctor = patients.get(ssn).getDoctor();

        if(doctor == null) {
            throw new NoSuchDoctor();
        }

        return doctor.getDocID();
    }

    /**
     * Retrieves the patients assigned to a doctor.
     */
    public Collection<String> getAssignedPatients(int id) throws NoSuchDoctor {
        if(!doctors.containsKey(id)) {
            throw new NoSuchDoctor();
        }

        return doctors.get(id).getPatients().stream()
            .map(Patient::getSsn)
            .collect(Collectors.toList());
    }

    /**
     * Loads data about doctors and patients from the given stream.
     */
    public int loadData(Reader reader) throws IOException {
        return loadData(reader, null);
    }

    /**
     * Loads data about doctors and patients from the given stream.
     */
    public int loadData(Reader reader, ErrorListener listener) throws IOException {
        int processed = 0;

        try(BufferedReader br = new BufferedReader(reader)) {
            String line;

            while((line = br.readLine()) != null) {
                if(processLine(line)) {
                    processed++;
                }
                else if(listener != null) {
                    listener.offending(line);
                }
            }
        }

        return processed;
    }

    private boolean processLine(String line) {
        String[] parts = line.split(";", -1);

        for(int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }

        if(parts.length == 4 && "P".equals(parts[0])) {
            if(hasEmpty(parts)) {
                return false;
            }

            addPatient(parts[1], parts[2], parts[3]);
            return true;
        }

        if(parts.length == 6 && "M".equals(parts[0])) {
            if(hasEmpty(parts)) {
                return false;
            }

            try {
                int docID = Integer.parseInt(parts[1]);
                addDoctor(parts[2], parts[3], parts[4], docID, parts[5]);
                return true;
            }
            catch(NumberFormatException e) {
                return false;
            }
        }

        return false;
    }

    private boolean hasEmpty(String[] parts) {
        for(String part : parts) {
            if(part == null || part.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the collection of doctors that have no patient at all.
     */
    public Collection<Integer> idleDoctors() {
        return doctors.values().stream()
            .filter(Doctor::isIdle)
            .sorted(Comparator.comparing(Doctor::getLast)
                .thenComparing(Doctor::getFirst)
                .thenComparingInt(Doctor::getDocID))
            .map(Doctor::getDocID)
            .collect(Collectors.toList());
    }

    /**
     * Retrieves the collection of doctors having a number of patients larger than the average.
     */
    public Collection<Integer> busyDoctors() {
        if(doctors.isEmpty()) {
            return java.util.Collections.emptyList();
        }

        double average = doctors.values().stream()
            .mapToInt(Doctor::nPatients)
            .average()
            .orElse(0.0);

        return doctors.values().stream()
            .filter(d -> d.nPatients() > average)
            .map(Doctor::getDocID)
            .collect(Collectors.toList());
    }

    /**
     * Retrieves the information about doctors and relative number of assigned patients.
     */
    public Collection<String> doctorsByNumPatients() {
        return doctors.values().stream()
            .sorted((d1, d2) -> {
                int cmp = Integer.compare(d2.nPatients(), d1.nPatients());

                if(cmp != 0) {
                    return cmp;
                }

                return Integer.compare(d1.getDocID(), d2.getDocID());
            })
            .map(Doctor::toStringStats)
            .collect(Collectors.toList());
    }

    /**
     * Retrieves the number of patients per speciality.
     */
    public Collection<String> countPatientsPerSpecialization() {
        Map<String, Integer> counts = new HashMap<>();

        for(Doctor doctor : doctors.values()) {
            int count = doctor.nPatients();

            if(count > 0) {
                counts.merge(doctor.getSpecialization(), count, Integer::sum);
            }
        }

        return counts.entrySet().stream()
            .sorted((e1, e2) -> {
                int cmp = Integer.compare(e2.getValue(), e1.getValue());

                if(cmp != 0) {
                    return cmp;
                }

                return e1.getKey().compareTo(e2.getKey());
            })
            .map(e -> String.format("%3d - %s", e.getValue(), e.getKey()))
            .collect(Collectors.toList());
    }
}