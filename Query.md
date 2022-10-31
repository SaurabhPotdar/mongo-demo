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