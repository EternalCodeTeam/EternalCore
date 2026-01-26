<div align="center">

![readme-banner.png](https://github.com/EternalCodeTeam/EternalCore/blob/master/assets/readme-banner.png?raw=true)

[![Available on SpigotMC](https://raw.githubusercontent.com/vLuckyyy/badges/main/available-on-spigotmc.svg)](https://www.spigotmc.org/resources/eternalcore-%E2%99%BE%EF%B8%8F-all-the-most-important-server-functions-in-one.112264/)
[![Available on Modrinth](https://raw.githubusercontent.com/vLuckyyy/badges/main/avaiable-on-modrinth.svg)](https://modrinth.com/plugin/eternalcore)
[![Available on Hangar](https://raw.githubusercontent.com/vLuckyyy/badges/main/avaiable-on-hangar.svg)](https://hangar.papermc.io/EternalCodeTeam/EternalCore)

[![Chat on Discord](https://raw.githubusercontent.com/vLuckyyy/badges/main//chat-with-us-on-discord.svg)](https://discord.com/invite/FQ7jmGBd6c)
[![Read the Docs](https://raw.githubusercontent.com/vLuckyyy/badges/main/read-the-documentation.svg)](https://docs.eternalcode.pl/eternalcore/introduction)
[![Available on BStats](https://raw.githubusercontent.com/vLuckyyy/badges/main/available-on-bstats.svg)](https://bstats.org/plugin/bukkit/EternalCore/13964)

[Report Bug](https://github.com/EternalCodeTeam/EternalCore/issues) • [Request Feature](https://github.com/EternalCodeTeam/EternalCore/issues) • [Join Discord](https://discord.com/invite/FQ7jmGBd6c)

<br>

<a href="https://ko-fi.com/eternalcodeteam">
  <img src="https://github.com/intergrav/devins-badges/blob/v3/assets/cozy/donate/kofi-plural-alt_64h.png?raw=true" height="64" alt="Support us on Ko-fi">
</a>

</div>

> [!IMPORTANT]
> 🚀 **EternalCore 2.0 has landed!**  
> This is a major release with internal changes, new systems, and config updates.  
> Please read the **changelog** carefully before upgrading:  
> 👉 https://github.com/EternalCodeTeam/EternalCore/releases/latest


# Welcome to EternalCore! 🚀

EternalCore is a modern, actively maintained alternative to **EssentialsX**.
It provides the most important server features in one plugin, with better configuration, performance, and support for the latest Minecraft versions.

## ℹ️ Information

- EternalCore fully supports Minecraft's latest minor versions starting from each major version, starting from 1.19 onward, e.g. `1.19.4`, `1.20.6`, `1.21.11`.
- Requires **Java 21 or later** to work properly. For older versions of Java, this may affect the functionality of the plugin.
- If you have any questions, perhaps you will find a solution to them in our [documentation](https://docs.eternalcode.pl/eternalcore/introduction.html), you can also ask us about it on [discord](https://discord.gg/FQ7jmGBd6c).

## 🛠️ Development Builds

Get the latest development builds from [our website](https://eternalcode.pl/builds?project=eternalcore)
## ✨ Features

- ⌨️ Over **80+** useful commands.
- ✅ Slot Bypass `(eternalcore.bypass.slot)`
- 💤 AFK System
- 💬 **Chat & Interaction**:
  - Admin Chat, Auto Messages, Chat On/Off, Slow Mode
  - Private Messaging (`/msg`, `/reply`, `/socialspy`, `/ignore`)
  - `/helpop` for player support
  - Advanced Notification System (Title, Actionbar, BossBar, etc.)
  - Custom Join, Quit, and Death messages
  - <details><summary>Server links feature (Click to see how it works)</summary><img src="assets/server-links-showcase.gif" alt="Server Links Showcase"></details>
- 🌌 **Teleportation & Travel**:
  - Complete Home, Warp, and Spawn System
  - Random Teleport (RTP) for wild exploration
  - Teleport Requests (TPA) and `/back` command
  - Navigation tools: `/tpup`, `/tprp`, `/tppos`, `/tphere`
- 🛡️ **Moderation & Administration**:
  - Jail, Freeze, and Vanish Systems
  - Admin Tools: `/sudo`, `/powertool`, `/butcher` (Mob Control)
  - Inventory Management: `/clear`, `/repair`, and Disposal (`/disposal`)
- 👤 **Player Management**:
  - Attributes: Gamemode, Fly, Speed, Godmode, Heal, Feed
  - Information: `/whois`, Playtime, Last Seen, Ping check
  - Utilities: Hat `/hat`, Skull `/skull`, Near players via `/near`
- � **Items & World**:
  - Open Utility Blocks (Workbench, Anvil, Enderchest, etc.)
  - Item Editing (Name, Lore, Flags) and Sign Editing
  - Time, Weather management
- ⚙️ **Core & Integration**:
  - 📄 PlaceholderAPI Support
  - 🗂️ Database Integration (PostgresSQL, SQLite, MySQL, MariaDB, H2)
  - 🌈 Adventure & [MiniMessage](https://docs.advntr.dev/minimessage/format.html) integration
  - 🔨 Advanced Configuration System
- [...and much more!](https://docs.eternalcode.pl/eternalcore/features.html)

## 👷 Developer API

To use the EternalCore API, you first need to add EternalCode to your project.
To do that, follow these steps:
You can check the latest release number [here](https://github.com/EternalCodeTeam/EternalCore/releases/latest).

1. Add repository:

For Gradle projects use:
```kts
maven("https://repo.eternalcode.pl/releases")
```

For Maven projects use:
```xml
<repository>
    <id>eternalcode-reposilite-releases</id>
    <url>https://repo.eternalcode.pl/releases</url>
</repository>
```

2. Add dependency:

For Gradle projects use:
```kts
compileOnly("com.eternalcode:eternalcore-api:2.0.0")
```

For Maven projects use:
```xml
<dependency>
    <groupId>com.eternalcode</groupId>
    <artifactId>eternalcore-api</artifactId>
    <version>2.0.0</version>
    <scope>provided</scope>
</dependency>
```

3. You are ready to use EternalCoreAPI in your project.

## 🏗️ Building

To build EternalCore, follow these steps (Make sure you have **JDK 21 or higher**):

```shell
./gradlew clean eternalcore-plugin:shadowJar
```
- The output file will be located at `eternalcore-plugin/libs`.


## 🐙 Contributing

Create a public fork of EternalCore, make changes and then create
a [Pull Request](https://github.com/EternalCodeTeam/EternalCore/pulls) with your appropriate changes.
See [CONTRIBUTING.md](https://github.com/EternalCodeTeam/EternalCore/blob/master/.github/CONTRIBUTING.md) to find out
more.

## ❤️ Special Thanks

[<img src="https://user-images.githubusercontent.com/65517973/210912946-447a6b9a-2685-4796-9482-a44bffc727ce.png" alt="JetBrains" width="150">](https://www.jetbrains.com)

We express our gratitude to JetBrains for providing [Open Source Licenses](https://www.jetbrains.com/opensource/) for their outstanding tools. We recommend using [IntelliJ IDEA](https://www.jetbrains.com/idea/) to work with our projects and boost your productivity!

[<img src="https://www.yourkit.com/images/yklogo.png" alt="YourKit" width="150">](https://www.yourkit.com)

YourKit supports our open-source projects with their advanced tools for profiling and monitoring.  
Creators of the [YourKit Java Profiler](https://www.yourkit.com/java/profiler/), [.NET Profiler](https://www.yourkit.com/dotnet-profiler/) and [YouMonitor](https://www.yourkit.com/youmonitor/).

