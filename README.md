<div align="center">
  <br>
  <h1>Spiral Knights Headless Client</h1>
  <h4>
    A fully programmatic Spiral Knights client, designed for server environments, </br>
    processing game events entirely through code.
  </h4>
</div>

<p align="center">
  <a href="https://maven.azzerial.net/#/releases/net/azzerial/spiral-knights-headless-client">
    <img src="https://img.shields.io/maven-metadata/v?style=flat-square&color=blue&label=Release&metadataUrl=https%3A%2F%2Fmaven.azzerial.net%2Freleases%2Fnet%2Fazzerial%2Fspiral-knights-headless-client%2Fmaven-metadata.xml">
  </a>
  <!-- a href="https://maven.azzerial.net/#/snapshots/net/azzerial/spiral-knights-headless-client">
    <img src="https://img.shields.io/maven-metadata/v?style=flat-square&color=green&label=Snapshot&metadataUrl=https%3A%2F%2Fmaven.azzerial.net%2Fsnapshots%2Fnet%2Fazzerial%2Fspiral-knights-headless-client%2Fmaven-metadata.xml">
</a -->
  <a href="https://github.com/azzerial/spiral-knights-headless-client/blob/main/LICENSE">
    <img src="https://img.shields.io/github/license/azzerial/spiral-knights-headless-client?style=flat-square&color=lightgray&label=License&logo=apache">
  </a>
</p>

<p align="center">
  <a href="#features">Features</a> •
  <a href="#next-steps">Next Steps</a> •
  <a href="#how-to-use">How To Use</a> •
  <a href="#installation">Installation</a> •
  <a href="#license">License</a>
</p>

<br>

## Features

As of version `1.0.0`, the headless client supported game features & services are:

- `exchange market` - all events related to the [energy depot market tab](https://wiki.spiralknights.com/Energy_Depot#Market)

## Next steps

Here are the next feature I consider implementing:

- `auction house` - ability to retrieve the auction house listings
- `game server redirection` - as of version `1.0.0` the client does not support game server redirection (handles it as a logout)
- `game server regions` - as of version `1.0.0`, the server IP the client connects to is hard coded ; once the game server redirection is handled, it will be possible to use the `Region` enum to select a server

It would also be possible to turn this "read only" headless client into a bot allowing for:

- `exchange market` - ability to buy or sell energy offers
- `auction house` - ability to bid, cancel a bid, get your listings, get your bids, list an item

Though I am not too keen on bringing those features to life as they would introduce botting to the game.

## How to Use

> [!NOTE]
> For more details regarding the library, please head to the [javadoc](https://maven.azzerial.net/javadoc/releases/net/azzerial/spiral-knights-headless-client/1.0.0).

```java
// create the SKClient
final SKClient client = SKClientBuilder.create(username, password)
    .enableServices(Service.EXCHANGE)
    .build();

// register your event listeners
client.addEventListeners(new ListenerAdapter() {
    @Override
    public void onExchange(@NotNull ExchangeEvent event) {
        System.out.println("Market last price is: " + event.market.lastPrice);
    }
});

// bring the client online
client.connect();
```

Look at the [`:playground` module's `Main`](https://github.com/azzerial/spiral-knights-headless-client/blob/main/playground/src/main/java/net/azzerial/skhc/playground/Main.java) for a more complete example.

## Installation

> [!IMPORTANT]
> Do not forget to replace the `REPOSITORY` and `VERSION` keywords. <br>
> The `VERSION` should be changed to the desired version of the project and the `REPOSITORY` must be set to `snapshots` - if the chosen version is a snapshot - otherwise to `releases`.
>
> **e.g.** <br>
> if `VERSION` = `1.0.0` then `REPOSITORY` = `releases` <br>
> if `VERSION` = `1.0-SNAPSHOT` then `REPOSITORY` = `snapshots`

> [!NOTE]
> You can import individual modules by using the structure `spiral-knights-headless-client-MODULE` as the artifact id, where the `MODULE` keyword should be replaced with the module's name.
>
> **e.g.** `spiral-knights-headless-client-core`, `spiral-knights-headless-client-legacy`

### Gradle

```kotlin
repositories {
    maven { url = uri("https://maven.azzerial.net/REPOSITORY") }
}

dependencies {
    implementation("net.azzerial:spiral-knights-headless-client:VERSION")
}
```

### Maven

```xml
<repositories>
  <repository>
    <url>https://maven.azzerial.net/REPOSITORY</url>
  </repository>
</repositories>

<dependency>
  <groupId>net.azzerial</groupId>
  <artifactId>spiral-knights-headless-client</artifactId>
  <version>VERSION</version>
</dependency>
```

## License

This project is licensed under the [Apache License 2.0](LICENSE) © 2025 [Robin Mercier](https://github.com/azzerial).

---

<p align="center">
  <code>Spiral Knights Headless Client</code> by <a href="https://github.com/Azzerial">Robin Mercier</a> •
  <a href="https://azzerial.net">azzerial.net</a>
</p>
