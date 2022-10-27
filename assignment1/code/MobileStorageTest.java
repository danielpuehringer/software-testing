package at.ac.tuwien.inso.peso;

import at.ac.tuwien.inso.peso.exception.NotEnoughSpaceException;
import at.ac.tuwien.inso.peso.exception.StorageEmptyException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests the implementation of {@link MobileStorage}
 */
public class MobileStorageTest {
    //TODO implement your tests here

    @Test
    public void storageConstructor_shouldThrowIllegalArgumentException() {
        IllegalArgumentException invalidStorageSize = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MobileStorage storage = new MobileStorage(-1);
        });
        Assertions.assertEquals("Storage size must be greater than 0", invalidStorageSize.getMessage());


        IllegalArgumentException invalidStorageSize2 = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MobileStorage storage = new MobileStorage(0);
        });
        Assertions.assertEquals("Storage size must be greater than 0", invalidStorageSize2.getMessage());


        IllegalArgumentException invalidStringException1 = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MobileStorage storage = new MobileStorage(1);
            storage.saveMessage("");
        });
        Assertions.assertEquals("Message cannot be null or empty", invalidStringException1.getMessage());

        IllegalArgumentException invalidStringException2 = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MobileStorage storage = new MobileStorage(1);
            storage.saveMessage(null);
        });
        Assertions.assertEquals("Message cannot be null or empty", invalidStringException2.getMessage());
    }

    @Test
    public void saveMessage_shouldSaveSingleShortMessage(){//NOTE: this test would initially fail, this shows me that there is a bug.
        MobileStorage storage = new MobileStorage(1);
        String message = "One short Message";
        storage.saveMessage(message);
        Assertions.assertEquals(message, storage.listMessages());
    }

    @Test
    public void saveMessage_shouldSaveMultipleShortMessages() {
        MobileStorage storage = new MobileStorage(5);
        String stringToBeSaved1 = "First Test";
        String stringToBeSaved2 = "Second Test";
        String stringToBeSaved3 = "Third Test";
        String stringToBeSaved4 = "null";//valid string
        String stringToBeSaved5 = "Fifth Test";
        storage.saveMessage(stringToBeSaved1);
        storage.saveMessage(stringToBeSaved2);
        storage.saveMessage(stringToBeSaved3);
        storage.saveMessage(stringToBeSaved4);
        storage.saveMessage(stringToBeSaved5);
        String expectedResult = stringToBeSaved1+"\n"+stringToBeSaved2+"\n"+stringToBeSaved3+"\n"+stringToBeSaved4+"\n"+stringToBeSaved5;
        Assertions.assertEquals(expectedResult, storage.listMessages());
    }

    @Test
    public void saveMessage_shouldSaveMultipleShorterAndLongerMessages(){
        MobileStorage storage = new MobileStorage(5);
        String shortMessage1 = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.158";
        String shortMessage2 = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua..159";
        String shortMessage3 = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua...160";
        String longMessage2 = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua....161";

        storage.saveMessage(shortMessage1);
        storage.saveMessage(shortMessage2);
        storage.saveMessage(shortMessage3);
        storage.saveMessage(longMessage2);
        String expectedResult = shortMessage1+"\n"+shortMessage2+"\n"+shortMessage3+"\n"+longMessage2;
        Assertions.assertEquals(expectedResult, storage.listMessages());
        Assertions.assertEquals(5, storage.getOccupied());
    }

    @Test
    public void saveMessage_shouldThrowNotEnoughSpaceExceptionForShortAndLongMessages(){
        MobileStorage storage = new MobileStorage(5);
        String shortMessage1 = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua..159";
        String shortMessage2 = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua...160";
        String longMessage1 = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua....161";

        storage.saveMessage(shortMessage1);
        storage.saveMessage(shortMessage2);
        storage.saveMessage(longMessage1);

        NotEnoughSpaceException notEnoughSpaceForTwoMessages = Assertions.assertThrows(NotEnoughSpaceException.class, ()->{
            String longMessage2 = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.....162";
            storage.saveMessage(longMessage2);//2 long and 2 short messages --> size 6 needed, therefore should crash
        });
        Assertions.assertEquals("Storage Overflow", notEnoughSpaceForTwoMessages.getMessage());
    }

    @Test
    public void saveMessage_shouldThrowNotEnoughSpaceExceptionForShortMessages(){
        MobileStorage storage = new MobileStorage(3);
        String stringToBeSaved1 = "First Test";
        String stringToBeSaved2 = "Second Test";
        String stringToBeSaved3 = "Third Test";
        storage.saveMessage(stringToBeSaved1);
        storage.saveMessage(stringToBeSaved2);
        storage.saveMessage(stringToBeSaved3);

        NotEnoughSpaceException notEnoughSpace = Assertions.assertThrows(NotEnoughSpaceException.class, ()->{
            String stringToBeSaved4 = "Fourth Test";
            storage.saveMessage(stringToBeSaved4);
        });
        Assertions.assertEquals("Storage Overflow", notEnoughSpace.getMessage());
    }

    @Test
    public void getOccupied_shouldReturnOccupiedStorage(){
        MobileStorage storage = new MobileStorage(3);
        Assertions.assertEquals(0, storage.getOccupied());
        String shortMessage = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua...160";
        String longMessage = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua....161";
        storage.saveMessage(longMessage);
        Assertions.assertEquals(2, storage.getOccupied());
        storage.saveMessage(shortMessage);
        Assertions.assertEquals(3, storage.getOccupied());
    }

    @Test
    public void deleteMessage_shouldDeleteOneShortAndOneLongMessage(){//NOTE: When first running this test, I discovered a 'java.lang.ArrayIndexOutOfBoundsException: Index 6 out of bounds for length 6' at 'at at.ac.tuwien.inso.peso.MobileStorage.deleteMessage(MobileStorage.java:93)'
        MobileStorage storage = new MobileStorage(6);
        String shortMessage1 = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua..159";
        String shortMessage2 = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua...160";
        String longMessage1 = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua....161";
        String longMessage2 = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.....162";

        storage.saveMessage(shortMessage1);
        storage.saveMessage(longMessage1);
        Assertions.assertEquals(3, storage.getOccupied());
        storage.saveMessage(shortMessage2);
        Assertions.assertEquals(4, storage.getOccupied());
        storage.saveMessage(longMessage2);
        Assertions.assertEquals(6, storage.getOccupied());
        String expectedResultBeforeDelete = shortMessage1+"\n"+longMessage1+"\n"+shortMessage2+"\n"+longMessage2;
        Assertions.assertEquals(expectedResultBeforeDelete, storage.listMessages());

        storage.deleteMessage();
        Assertions.assertEquals(5, storage.getOccupied());
        String expectedResultAfterFirstDelete = longMessage1+"\n"+shortMessage2+"\n"+longMessage2;
        Assertions.assertEquals(expectedResultAfterFirstDelete, storage.listMessages());

        storage.deleteMessage();//NOTE: there is an 'feature' here because longer messages are not deleted properly. this behaviour is not a bug but a specified behaviour
        Assertions.assertEquals(4, storage.getOccupied());
        String expectedResultAfterSecondDelete = "1\n"+shortMessage2+"\n"+longMessage2;//the assignment says, that this is the proper behaviour for multi message messages.
        Assertions.assertEquals(expectedResultAfterSecondDelete, storage.listMessages());
    }

    @Test
    public void deleteMessage_shouldThrowStorageEmptyExceptionAfterDeletingAllMessages(){
        MobileStorage storage = new MobileStorage(3);
        String shortMessage1 = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua...160";
        String longMessage1 = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua....161";
        storage.saveMessage(shortMessage1);
        Assertions.assertEquals(1, storage.getOccupied());
        storage.saveMessage(longMessage1);
        Assertions.assertEquals(3, storage.getOccupied());
        storage.deleteMessage();
        Assertions.assertEquals(2, storage.getOccupied());
        storage.deleteMessage();
        Assertions.assertEquals(1, storage.getOccupied());//delete method works as required in the assignment
        storage.deleteMessage();
        Assertions.assertEquals(0, storage.getOccupied());

        StorageEmptyException storageEmptyException = Assertions.assertThrows(StorageEmptyException.class, ()->{
            storage.deleteMessage();
        });
        Assertions.assertEquals("There are no messages in the inbox", storageEmptyException.getMessage());
    }

    @Test
    public void search_shouldFindSearchedTexts(){//NOTE: when first running this test I got a NPE: 'java.lang.NullPointerException: Cannot invoke "Object.equals(Object)" because the return value of "at.ac.tuwien.inso.peso.MobileMessage.getPredecessor()" is null' at 'at at.ac.tuwien.inso.peso.MobileStorage.lambda$getLastMessage$3(MobileStorage.java:157)'
        MobileStorage storage = new MobileStorage(5);
        String shortMessage1 = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua...160";
        String longMessage1 = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua....161";
        String longMessage2 = "Worem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua....161";
        storage.saveMessage(shortMessage1);
        storage.saveMessage(longMessage1);
        storage.saveMessage(longMessage2);
        Assertions.assertEquals(5, storage.getOccupied());
        String stringToSearchForLorem = "Lorem ipsum";
        String stringToSearchForWorem = "Worem ipsum";

        String expectedResultLorem = shortMessage1+"\n"+longMessage1;
        String expectedResultWorem = longMessage2;
        Assertions.assertEquals(expectedResultLorem, storage.search(stringToSearchForLorem));
        Assertions.assertEquals(expectedResultWorem, storage.search(stringToSearchForWorem));
    }

    @Test
    public void search_shouldNotFindSearchedText(){
        MobileStorage storage = new MobileStorage(5);
        String shortMessage1 = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua...160";
        String longMessage1 = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua....161";
        String longMessage2 = "Worem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua....161";
        storage.saveMessage(shortMessage1);
        storage.saveMessage(longMessage1);
        storage.saveMessage(longMessage2);
        Assertions.assertEquals(5, storage.getOccupied());

        String neverOccurringString = "This text never occurs in any of the messages";

        String expectedResult = "";
        Assertions.assertEquals(expectedResult, storage.search(neverOccurringString));
    }

    @Test
    public void searchOccurences_shouldFindGivenOccurrences(){//NOTE: when first running this test I got a NPE: 'java.lang.NullPointerException: Cannot invoke "Object.equals(Object)" because the return value of "at.ac.tuwien.inso.peso.MobileMessage.getPredecessor()" is null' at 'at at.ac.tuwien.inso.peso.MobileStorage.lambda$getLastMessage$3(MobileStorage.java:157)'
        MobileStorage storage = new MobileStorage(5);
        String shortMessage1 = "Lorem Lorem lorem sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua...160";
        String longMessage1 = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua....161";
        String longMessage2 = "Worem lorem dorem sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua....161";
        storage.saveMessage(shortMessage1);
        storage.saveMessage(longMessage1);
        storage.saveMessage(longMessage2);
        Assertions.assertEquals(5, storage.getOccupied());
        String stringToSearchFor = "Lorem";
        String neverOccurringString = "This text never occurs in any of the messages";

        Assertions.assertEquals(2, storage.searchOccurences(stringToSearchFor));
        Assertions.assertEquals(0, storage.searchOccurences(neverOccurringString));
    }

}
