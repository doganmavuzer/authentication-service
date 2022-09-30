print("Started Adding the Users.");
db = db.getSiblingDB("test");
db.createUser({
    user: "mavuzer",
    pwd: "mongo",
    roles: [{ role: "readWrite", db: "test" }],
});
print("End Adding the User Roles.");