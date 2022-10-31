# Quering in Mongo DB

## Database -> Collection(Table) -> Document(Entity)

## Connecting to Atlas cluster

```mongo "mongodb+srv://<username>:<password>@<cluster>.mongodb.net/admin"```

## Export

1. ```mongodump --uri "mongodb+srv://<your username>:<your password>@<your cluster>.mongodb.net/sample_supplies"``` -> exports in BSON
2. ```mongoexport --uri="mongodb+srv://<your username>:<your password>@<your cluster>.mongodb.net/sample_supplies" --collection=sales --out=sales.json``` -> exports in JSON

## Import

1. ```mongorestore --uri "mongodb+srv://<your username>:<your password>@<your cluster>.mongodb.net/sample_supplies"  --drop dump"``` -> imports BSON data
2. ```mongoimport --uri="mongodb+srv://<your username>:<your password>@<your cluster>.mongodb.net/sample_supplies" --drop sales.json``` -> imports JSON data

## Show

1. ```show dbs```
2. ```use sample_training```
3. ```show collections```
4. ```db.zips.find({"state": "NY"})``` -> Will show the first 20 records, use ```"it"``` to get the next 20 records

## Inserting

```db.inspections.insert([{ "_id": 1, "test": 1 },{ "_id": 1, "test": 2 },{ "_id": 3, "test": 3 }])```

### Insertion order

By default ```ordered=false```\
```ordered=false``` will fail fast on duplicate id i.e it stops as soon as we get duplicate id\
```ordered=true``` will show error for duplicate id, but will continue inserting other records\
e.g. when inserting ```_id=1,_id=1,_id=2```, ordered=false will only insert ```_id=1``` and as it stops when duplicate id is encountered.
ordered=true will insert ```_id=1,_id=2```

## Fitering

1. ```db.zips.find({"state": "NY", "city": "ALBANY"}).pretty()```
2. ```db.zips.find({state: "NY", city: "ALBANY"}).pretty()``` -> Also works with semicolon
3. ```db.zips.findOne({state: "NY", city: "ALBANY"}).pretty()```
4. ```db.zips.find({state: "NY", city: "ALBANY"}).count()```

### Query operators

```$eq, $ne, $gt, $lt, $gte, $lte```\
Syntax : ```{field:{operator:value}}```\
Find all documents where the tripduration was less than or equal to 70 seconds and the usertype was Customer using the **implicit** equality operator:

```text
db.trips.find({ "tripduration": { "$lte" : 70 },
                "usertype": "Customer" }).pretty()
```

### Logical operators

```$and, $or, $nor, $not```\
Syntax : ```{operator : [{statement1},{statement2},...]}```\
Find all documents where airplanes CR2 or A81 left or landed in the KZN airport:

```text
db.routes.find({ "$and": [ { "$or" :[ { "dst_airport": "KZN" },
                                    { "src_airport": "KZN" }
                                  ] },
                          { "$or" :[ { "airplane": "CR2" },
                                     { "airplane": "A81" } ] }
                         ]}).pretty()
```

## Arrays

1. ```{amenities: ["Shampoo", "WiFi"]}``` Matches all documents which have only Shampoo, WiFi. Order is important here.
2. ```{"amenities": {"$all": ["Shampoo", "WiFi"]}}``` Matches all documents which have atleast Shampoo, WiFi. Order is not important here.
3. Find all documents with exactly 20 amenities which include all the amenities listed in the query array:

    ```text
      db.listingsAndReviews.find({ "amenities": {
                                  "$size": 20,
                                  "$all": [ "Internet", "Wifi",  "Kitchen",
                                           "Heating", "Family/kid friendly",
                                           "Washer", "Dryer", "Essentials",
                                           "Shampoo", "Hangers",
                                           "Hair dryer", "Iron",
                                           "Laptop friendly workspace" ]
                                         }
                            }).pretty()
    ```

4. $elemMatch To query object inside array or project the object inside array. Can be used in query or projection in find().\
```{"scores":{"$elemMatch":{"score":{ "$gt": 85 }})``` Here scores is the array and score is a field in the object inside that array\
eg. Find all documents where the student in class 431 received a grade higher than 85 for any type of assignment:

    ```text
        db.grades.find(
          { "class_id": 431 },
          { "scores": { "$elemMatch": { "score": { "$gt": 85 } } }
        }).pretty()
    ```

## Projection

```db.collection.find(query, projection)```\
Use 1 to include field and 0 to exclude it.

1. ```db.collection.find(query, field1:1, field2:1)``` Only returns field1, field2 and _id
2. ```db.collection.find(query, field1:0, field2:0)``` Returns all the fields except field1 and field2
3. ```db.collection.find(query, field1:1, field2:1, _id:0)``` Only returns field1 and field2
