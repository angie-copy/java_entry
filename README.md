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









   Exercise 3: Group Products by Color
Problem: Group all products by their color and count how many products exist for each color.

Solution:

java
Copy
Edit
Map<String, Long> productsByColor = products.stream()
    .collect(Collectors.groupingBy(Product::getColor, Collectors.counting())); // Map<String, Long>

productsByColor.forEach((color, count) -> 
    System.out.println(color + ": " + count));
Explanation:

groupingBy groups products by color, resulting in a Map<String, List<Product>>.
counting replaces the list with a count, making the result a Map<String, Long>.
Exercise 4: List Product Names Sorted by Price
Problem: Get a list of product names, sorted by price in ascending order.

Solution:

java
Copy
Edit
List<String> sortedProductNames = products.stream()
    .sorted(Comparator.comparingDouble(Product::getPrice)) // Stream<Product>
    .map(Product::getName) // Stream<String>
    .collect(Collectors.toList()); // List<String>

System.out.println("Products sorted by price: " + sortedProductNames);
Explanation:

sorted sorts products by price (Stream<Product>).
map extracts product names, transforming it into a Stream<String>.
collect converts the stream into a List<String>.
Exercise 5: Total Inventory Value by Color
Problem: Calculate the total inventory value (price * amount) for each color.

Solution:

java
Copy
Edit
Map<String, Double> inventoryValueByColor = products.stream()
    .collect(Collectors.groupingBy(Product::getColor, // Map<String, List<Product>>
        Collectors.summingDouble(product -> product.getPrice() * product.getAmount()))); // Map<String, Double>

inventoryValueByColor.forEach((color, value) -> 
    System.out.println(color + ": $" + value));
Explanation:

groupingBy groups products by color (Map<String, List<Product>>).
summingDouble calculates the total value for each color, returning a Map<String, Double>.
Exercise 6: Find Products Above Average Price
Problem: Get a list of products that have a price higher than the average price.

Solution:

java
Copy
Edit
double averagePrice = products.stream()
    .mapToDouble(Product::getPrice) // DoubleStream
    .average() // OptionalDouble
    .orElse(0.0);

List<Product> aboveAverageProducts = products.stream()
    .filter(product -> product.getPrice() > averagePrice) // Stream<Product>
    .collect(Collectors.toList()); // List<Product>

aboveAverageProducts.forEach(product -> 
    System.out.println(product.getName() + ": $" + product.getPrice()));
Explanation:

mapToDouble and average calculate the average price (OptionalDouble).
filter keeps products above the average price (Stream<Product>).
collect gathers the filtered products into a List<Product>.
Exercise 7: Combine Products into a Summary String
Problem: Create a single string summarizing all product names and their prices.

Solution:

java
Copy
Edit
String summary = products.stream()
    .map(product -> product.getName() + " ($" + product.getPrice() + ")") // Stream<String>
    .collect(Collectors.joining(", ")); // String

System.out.println("Product summary: " + summary);
Explanation:

map transforms each product into a string representation (Stream<String>).
joining concatenates the strings with a comma separator, returning a String.
Exercise 8: Partition Products by Sale Status
Problem: Partition products into two groups: active for sale and not active for sale.

Solution:

java
Copy
Edit
Map<Boolean, List<Product>> partitionedProducts = products.stream()
    .collect(Collectors.partitioningBy(Product::isActive_for_sale)); // Map<Boolean, List<Product>>

partitionedProducts.forEach((isActive, productList) -> {
    System.out.println(isActive ? "Active for sale:" : "Not active:");
    productList.forEach(product -> 
        System.out.println(" - " + product.getName()));
});
Explanation:

partitioningBy separates products into two groups based on their sale status (Map<Boolean, List<Product>>).
Each boolean key (true/false) corresponds to a list of products.
Exercise 9: Find the First Product by Color
Problem: Find the first product of a specific color (e.g., "Red").

Solution:

java
Copy
Edit
String targetColor = "Red";
Optional<Product> firstRedProduct = products.stream()
    .filter(product -> product.getColor().equalsIgnoreCase(targetColor)) // Stream<Product>
    .findFirst(); // Optional<Product>

firstRedProduct.ifPresent(product -> 
    System.out.println("First red product: " + product.getName()));
Explanation:

filter keeps only products with the target color (Stream<Product>).
findFirst returns the first matching product as an Optional<Product>.












    
   
