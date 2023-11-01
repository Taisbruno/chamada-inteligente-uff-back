package br.com.smartroll.view;

import br.com.smartroll.model.PresenceModel;
import br.com.smartroll.repository.entity.PresenceEntity;
import com.google.gson.annotations.SerializedName;

/**
 * View de presença para o histórico de chamadas ocorridas.
 */
public class HistoricPresenceView {

    @SerializedName("id")
    public String id;
    @SerializedName("registration")
    public String studentRegistration;
    @SerializedName("name")
    public String name;
    @SerializedName("medicalCertificate")
    public String medicalCertificate;
    @SerializedName("message")
    public String message;
    @SerializedName("isPresent")
    public boolean isPresent;
    @SerializedName("entryTime")
    public String entryTime;
    @SerializedName("exitTime")
    public String exitTime;
    @SerializedName("timePresent")
    public String timePresent;
    @SerializedName("frequency")
    public double frequency;
    @SerializedName("failed")
    public boolean failed;

    public HistoricPresenceView(PresenceModel presenceModel) {
        this.id = presenceModel.id;
        this.studentRegistration = presenceModel.studentRegistration;
        this.name = presenceModel.name;
        this.medicalCertificate = presenceModel.medicalCertificate;
        this.message = presenceModel.message;
        this.isPresent = presenceModel.isPresent;
        this.entryTime = presenceModel.entryTime;
        this.exitTime = presenceModel.exitTime;
        this.timePresent = presenceModel.timePresent;
        this.frequency = presenceModel.frequency;
        this.failed = presenceModel.failed;
    }
}
