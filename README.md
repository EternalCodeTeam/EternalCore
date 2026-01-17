<div align="center">

![readme-banner.png](https://github.com/EternalCodeTeam/EternalCore/blob/master/assets/readme-banner.png?raw=true)

[![Available on SpigotMC](https://raw.githubusercontent.com/vLuckyyy/badges/main/available-on-spigotmc.svg)](https://www.spigotmc.org/resources/eternalcore-%E2%99%BE%EF%B8%8F-all-the-most-important-server-functions-in-one.112264/)
[![Available on Modrinth](https://raw.githubusercontent.com/vLuckyyy/badges/main/avaiable-on-modrinth.svg)](https://modrinth.com/plugin/eternalcore)
[![Available on Hangar](https://raw.githubusercontent.com/vLuckyyy/badges/main/avaiable-on-hangar.svg)](https://hangar.papermc.io/EternalCodeTeam/EternalCore)

[![Chat on Discord](https://raw.githubusercontent.com/vLuckyyy/badges/main//chat-with-us-on-discord.svg)](https://discord.com/invite/FQ7jmGBd6c)
[![Read the Docs](https://raw.githubusercontent.com/vLuckyyy/badges/main/read-the-documentation.svg)](https://docs.eternalcode.pl/eternalcore/introduction)
[![Available on BStats](https://raw.githubusercontent.com/vLuckyyy/badges/main/available-on-bstats.svg)](https://bstats.org/plugin/bukkit/EternalCore/13964)
</div>

> [!WARNING]
> **This branch is currently under active development for EternalCore 2.0.**
> 
> This is a major update that includes breaking changes, configuration refactors, translation cleanup, and UX improvements.
> 
> ⚠️ Features may be incomplete or unstable. Avoid using in production unless you're contributing or testing the new version.
> 
> 🔗 See the full roadmap and discussion: https://github.com/EternalCodeTeam/EternalCore/issues/952



# Welcome to EternalCore! 🚀

EternalCore is your ultimate companion for enhancing your Minecraft server experience. 💎 Crafted with care, EternalCore is dedicated to incorporating essential and highly practical server functions into a single plugin.

## ℹ️ Information

- EternalCore fully supports Minecraft's latest minor versions starting from each major version, starting from 1.19 onward, e.g. `1.19.4`, `1.20.6`, `1.21.11`.
- Requires **Java 21 or later** to work properly. For older versions of Java, this may affect the functionality of the plugin.
- If you have any questions, perhaps you will find a solution to them in our [documentation](https://docs.eternalcode.pl/eternalcore/introduction.html), you can also ask us about it on [discord](https://discord.gg/FQ7jmGBd6c).

## 🛠️ Development Builds

Get the latest development builds from our [GitHub Actions](https://github.com/EternalCodeTeam/EternalCore/actions?query=branch%3Amaster).

<details><summary>🎥 Video Guide</summary>
  <img src="https://i.imgur.com/hmv38VS.gif" alt="Video Guide">
</details>

## ✨ Features

- ⌨️ Over **80+** useful commands.
- ✅ Slot Bypass `(eternalcore.bypass.slot)`
- 💤 AFK System
- 💬 Chat Features, including:
  - Admin Chat
  - Auto Messages System (with a sequence or random options)
  - Chat On/Off Switch
  - Chat Slow Mode
  - /ignore and /unignore (with `-all` option)
  - /msg, /msgtoggle, /socialspy, and /reply commands
  - /helpop command
  - Advanced Notification System allowing you to customize every message to your liking (Title, Subtitle, Actionbar, Chat, etc.) 
- 🔨 Open Utility Blocks with simple commands like `/workbench`
- 💼 ~~Player Inventory Viewer~~ (We recommend using: [OpenInv](https://github.com/Jikoo/OpenInv))
- ❤️ Player Attribute Management, including healing, feed, teleportation, and godmode modification
- 🏓 Ping Command to check client-server connectivity
- 👤 Player Information Command (`/whois`)
- 🏠 Home, Warp, and Spawn System
- 📄 PlaceholderAPI Support
- 📝 Customizable and Translatable Messages (Player language selection available)
- <details><summary>Server links feature (Click to see how it works)</summary><img src="assets/server-links-showcase.gif" alt="Server Links Showcase"></details>
- ⚙️ Advanced Configuration System for customization
- 🗂️ Database Integration (PostgresSQL, SQLite, MySQL, MariaDB, H2)
- 🌈 Adventure and [MiniMessage](https://docs.advntr.dev/minimessage/format.html) integration with legacy color processing (e.g., &7, &e)
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
compileOnly("com.eternalcode:eternalcore-api:2.0.0-SNAPSHOT")
```

For Maven projects use:
```xml
<dependency>
    <groupId>com.eternalcode</groupId>
    <artifactId>eternalcore-api</artifactId>
    <version>2.0.0-SNAPSHOT</version>
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

