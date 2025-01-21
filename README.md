# Sum of Values by Name Group the records by name and calculate the sum of value for each name.

  - Hint: Use Collectors.groupingBy and Collectors.summingInt.
  - Map<String, Integer> sumByName = records.stream()
    .collect(Collectors.groupingBy(
        Record::getName,
        Collectors.summingInt(Record::getValue)
    ));

# Filter Records by Value Threshold
  - List<Record> filteredRecords = records.stream()
    .filter(record -> record.getValue() >= 10)
    .collect(Collectors.toList());

# Find the Maximum Value by Name
 - Map<String, Optional<Record>> maxByName = records.stream()
    .collect(Collectors.groupingBy(
        Record::getName,
        Collectors.maxBy(Comparator.comparingInt(Record::getValue))
    ));

# Sort Records by Name and Value
 - List<Record> sortedRecords = records.stream()
    .sorted(Comparator.comparing(Record::getName)
        .thenComparing(Record::getValue))
    .collect(Collectors.toList());

