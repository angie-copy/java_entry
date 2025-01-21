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


1. Sum of products by color
   Map<String, Integer> sumByColor = products.stream()
   .collect(Collectors.groupingBy(Product::getColor, Collectors.summinInt(Product::getAmount)));


   Collectors.groupingBy(Product::getColor, Collectors.summingInt(Product::getAmount))
   Collectors.groupingBy groups the products by a key, which here is the color 
   (Product::getColor).

   Collectors.summingInt(Product::getAmount) sums up the amounts of products within each color 
   group.
   [
    {color: "Red", amount: 5},
    {color: "Red", amount: 10},
    {color: "Blue", amount: 7}
]
{
    "Red": 15,
    "Blue": 7
}

2. group products by active status
   Map<Boolean, List<Product>> groupedByActiveStatus = products.stream()
   .collect(Collectors.partitioningBy(Product::isActive_for_sale));
   
Collectors.partitioningBy(Product::isActive_for_sale)

This partitions the products into two groups based on whether isActive_for_sale is true or false.
Each group contains a list of products.
Output: A Map<Boolean, List<Product>>, where:
Key (Boolean): true or false (active or inactive).
Value (List<Product>): List of products in each group.

3. count products by active status
Map<Boolean, Long> countByActiveStatus = products.stream()
.collect(Collectors.partitioningBy(Product::isActive_for_sale, Collectors.counting()));


Collectors.partitioningBy(Product::isActive_for_sale, Collectors.counting())

4. average price of products
    double averagePrice = products.stream()
 .collect(Collectors.averagingDouble(Product::getPrice));

5. total amount of products
         int totalAmount = products.stream()
   .mapToInt(Product::getAmount).sum();












    
   
