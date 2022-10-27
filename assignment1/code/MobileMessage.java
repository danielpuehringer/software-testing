package at.ac.tuwien.inso.peso;

/**
 * Represents a mobile message that is stored at one storage position.
 */
public class MobileMessage {

    //stores the content of this message
    private final String text;

    //in case of multi-part-messages, refers to the preceding message
    //is null in case of single message
    private MobileMessage predecessor;

    public MobileMessage(String text, MobileMessage predecessor) {
        this.text = text;
        this.predecessor = predecessor;
    }

    public String getText() {
        return text;
    }

    public MobileMessage getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(MobileMessage predecessor) {
        this.predecessor = predecessor;
    }
}
