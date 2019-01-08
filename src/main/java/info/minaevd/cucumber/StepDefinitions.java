package info.minaevd.cucumber;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import info.minaevd.kafka.Consumer;
import info.minaevd.kafka.Producer;

public class StepDefinitions
{
    private static final String TOPIC_PRODUCER = "src";

    private static final String TOPIC_SUBSCRIPTIONS = "control";

    private static final String TOPIC_CONSUMER = "dst";

    private final Producer producer = new Producer();

    private final Consumer consumer = new Consumer(TOPIC_CONSUMER);

    private Map<String, Integer> snapshot = new HashMap<>();

    @Given("^that we know a number of words processed previously$")
    public void getSnapshotForCounts()
    {
        producer.emit(TOPIC_SUBSCRIPTIONS, "subscribe qwe-asd-zxc-101");
        snapshot = getConsumerRecords();
    }

    @When("^I send a new word \"([^\"]*)\"$")
    public void sendWord( String word )
    {
        producer.emit(TOPIC_PRODUCER, word);
    }

    @Then("^I should receive count for \"([^\"]*)\" word increased by (\\d+)$")
    public void iShouldReceiveCountForWordIncreasedBy( String word, int delta )
    {
        Map<String, Integer> actual = getConsumerRecords();

        Map<String, Integer> expected = new HashMap<>();
        expected.put(word, snapshot.getOrDefault(word, 0) + delta);

        Assert.assertEquals("Rows in kafka doesn't match expected", expected, actual);
    }

    private Map<String, Integer> getConsumerRecords()
    {
        return consumer.consume();
    }
}
