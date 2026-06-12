package clinic;

import java.util.LinkedList;
import java.util.List;

public class Doctor extends Patient {

    private int docID;
    private String specialization;
    private List<Patient> patients = new LinkedList<>();

    public Doctor(String first, String last, String ssn, int docID, String specialization) {
        super(first, last, ssn);
        this.docID = docID;
        this.specialization = specialization;
    }

    public int getDocID() {
        return docID;
    }

    public void setDocID(int docID) {
        this.docID = docID;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void addPatient(Patient patient) {
        if(!this.patients.contains(patient)) {
            this.patients.add(patient);
        }
    }

    public void removePatient(Patient patient) {
        this.patients.remove(patient);
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public boolean isIdle() {
        return this.patients.isEmpty();
    }

    public int nPatients() {
        return patients.size();
    }

    @Override
    public String toString() {
        return super.toString() + " [" + docID + "]: " + specialization;
    }

    public String toStringStats() {
        return String.format("%3d : %d %s %s", nPatients(), docID, getLast(), getFirst());
    }
}