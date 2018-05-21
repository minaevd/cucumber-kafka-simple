# Cucumber tests for Flink project
Trying [Cucumber](https://cucumber.io/ "Cucumber") tests for [Flink](https://flink.apache.org/ "Flink") project with [Kafka](https://kafka.apache.org/ "Kafka") storage.

This project tests the following Flink project: https://github.com/minaevd/flink-wordcounter

---

It's a very basic Cucumber test for a sample word counter Flink application: get previous count of words, send a new word, verify the count incremented by 1.
