# Anuto (Android) – Tower Defense Engine & Game

**Anuto** ist ein vollständig eigenständiges Tower-Defense-Spiel für Android mit eigener Spiel‑Engine, eigener Render‑Pipeline und einer klar getrennten Architektur aus **Engine**, **Gameplay‑Logik** und **UI**. Der Fokus liegt auf flüssiger Darstellung, klarer Spielmechanik und einem datengetriebenen Content‑System für Karten und Wellen.

---

## 🧠 Warum dieses Projekt besonders ist

- **Eigene Engine statt Standard‑Framework**: Spiel‑Loop, Entity‑Store, Renderer, Sound‑ und Theme‑Management sind bewusst separat aufgebaut und orchestriert. (siehe `engine/*` und `GameFactory`)【F:app/src/main/java/ch/logixisland/anuto/engine/logic/GameEngine.java†L1-L157】【F:app/src/main/java/ch/logixisland/anuto/engine/render/Renderer.java†L1-L95】【F:app/src/main/java/ch/logixisland/anuto/GameFactory.java†L1-L140】
- **Datengetriebene Inhalte**: Karten und Wellen sind als JSON in `res/raw` abgelegt und zur Laufzeit geladen – leicht erweiterbar ohne Code‑Änderung an der Engine.【F:app/src/main/java/ch/logixisland/anuto/business/game/GameLoader.java†L82-L154】
- **Persistenter Spielfortschritt**: Autosave, manuelles Speichern (inkl. Screenshot), Migration und Highscore‑Tracking pro Karte gehören zum Kernsystem.【F:app/src/main/java/ch/logixisland/anuto/business/game/GameSaver.java†L24-L86】【F:app/src/main/java/ch/logixisland/anuto/business/game/HighScores.java†L1-L55】

---

## 🎮 Gameplay‑Highlights

- **Wellen‑System mit Boni & dynamischer Schwierigkeit** (Belohnung, Early‑Bonus, Tower‑Aging).【F:app/src/main/java/ch/logixisland/anuto/business/wave/WaveManager.java†L1-L200】
- **Aufrüsten, Verstärken, Strategiewechsel und Lock‑Target** für Türme via Tower‑Control‑Layer.【F:app/src/main/java/ch/logixisland/anuto/business/tower/TowerControl.java†L1-L145】
- **Mehrere Gegnertypen** (u. a. Blob, Flyer, Healer, Soldier, Sprinter).【F:app/src/main/java/ch/logixisland/anuto/GameFactory.java†L69-L98】
- **Vielfältige Tower‑Rollen** (Kanonen, Laser‑Varianten, Mörser, Minen, Raketen, Kleber, Teleport).【F:app/src/main/java/ch/logixisland/anuto/GameFactory.java†L86-L104】
- **Tutorial‑Flow** mit kontextbezogenen Hinweisen, steuerbar per Settings.【F:app/src/main/java/ch/logixisland/anuto/business/game/TutorialControl.java†L1-L197】

---

## 🏗 Architektur – Das System in Schichten

### 1) Engine‑Layer (`engine/*`)
- **Game Loop & Ticks**: deterministische Spielschleife mit Message‑Queue und Target‑Framerate.【F:app/src/main/java/ch/logixisland/anuto/engine/logic/GameEngine.java†L1-L157】
- **Rendering**: Canvas‑basierter Renderer mit Viewport‑Mapping, Clip‑Rect und Screenshot‑Support (für Savegames).【F:app/src/main/java/ch/logixisland/anuto/engine/render/Renderer.java†L1-L95】
- **Sound & Theme**: Preference‑gesteuertes Sound‑Enablement und dynamisches Theme‑Switching.【F:app/src/main/java/ch/logixisland/anuto/engine/sound/SoundManager.java†L1-L42】【F:app/src/main/java/ch/logixisland/anuto/engine/theme/ThemeManager.java†L1-L90】

### 2) Business‑Layer (`business/*`)
- **Spielzustand & Scoreboard**: Credits, Lives, Early‑Bonus/Wave‑Bonus, Highscores und Persistenz.【F:app/src/main/java/ch/logixisland/anuto/business/game/ScoreBoard.java†L1-L117】【F:app/src/main/java/ch/logixisland/anuto/business/game/HighScores.java†L1-L55】
- **Wellenlogik**: Start/Stop‑Timing, Belohnungen, Gegner‑Spawning und Zähler‑Tracking.【F:app/src/main/java/ch/logixisland/anuto/business/wave/WaveManager.java†L1-L200】
- **Speichern & Laden**: Automatisches Speichern, Load mit Migration, Map‑Initialisierung und Save‑Screenshots.【F:app/src/main/java/ch/logixisland/anuto/business/game/GameSaver.java†L24-L86】【F:app/src/main/java/ch/logixisland/anuto/business/game/GameLoader.java†L82-L154】

### 3) Entity‑Layer (`entity/*`)
- **Einheiten als Entities**: Towers, Enemies, Shots und Effects mit eigener Logik und Persistenz (registriert zentral in `GameFactory`).【F:app/src/main/java/ch/logixisland/anuto/GameFactory.java†L69-L118】

### 4) UI‑Layer (`view/*`)
- **GameActivity + GameView**: Startet die Engine, bindet den Renderer, verarbeitet Touch & Drag‑Drop für Tower‑Placement.【F:app/src/main/java/ch/logixisland/anuto/view/game/GameActivity.kt†L1-L89】【F:app/src/main/java/ch/logixisland/anuto/view/game/GameView.java†L1-L107】

---

## 🗺 Content & Datenformate

- **Karten**: JSON‑Definitionen in `res/raw` (z. B. `map_original.json`) und zentral verwaltet in `MapRepository`.【F:app/src/main/java/ch/logixisland/anuto/business/game/MapRepository.java†L1-L52】
- **Wellen**: zentrale Wellenliste in `res/raw/waves.json`, geladen bei jedem Spielstart.【F:app/src/main/java/ch/logixisland/anuto/business/game/GameLoader.java†L118-L132】
- **Audio**: Soundeffekte liegen als `.ogg` in `res/raw`. (vgl. SoundFactory & SoundManager).【F:app/src/main/java/ch/logixisland/anuto/engine/sound/SoundManager.java†L1-L42】

---

## 🚀 Build & Run (lokal)

**Voraussetzungen**
- Android Studio (aktuelles Stable‑Release)
- JDK 17
- Android SDK 36 (compileSdk/targetSdk)

**Build‑Befehle**
```bash
./gradlew assembleDebug
```

**Auf ein Gerät/Emulator installieren**
```bash
./gradlew installDebug
```

**Hinweis:** Die App benötigt `minSdk = 36` und ist für moderne Android‑Versionen optimiert.【F:app/build.gradle†L6-L42】

---

## 🧩 Erweiterungspunkte

- **Neue Maps hinzufügen**: JSON in `res/raw` ablegen und in `MapRepository` registrieren.【F:app/src/main/java/ch/logixisland/anuto/business/game/MapRepository.java†L1-L52】
- **Neue Entities**: Factory & Persister implementieren und in `GameFactory#registerEntities()` eintragen.【F:app/src/main/java/ch/logixisland/anuto/GameFactory.java†L69-L118】
- **Neue Wellen**: `waves.json` erweitern, Wave‑Parsing erfolgt automatisch beim Laden.【F:app/src/main/java/ch/logixisland/anuto/business/game/GameLoader.java†L118-L132】

---

## 🔍 Projektfakten (aus dem Code)

- **Sprache**: Kotlin (UI‑Layer) + Java (Engine/Gameplay).【F:app/src/main/java/ch/logixisland/anuto/view/game/GameActivity.kt†L1-L89】【F:app/src/main/java/ch/logixisland/anuto/GameFactory.java†L1-L140】
- **Architektur**: klare Trennung von Engine, Business‑Logik, Entities und UI. (siehe Paketstruktur).【F:app/src/main/java/ch/logixisland/anuto/GameFactory.java†L1-L140】
- **Persistenz**: Savegames inkl. Migration und Highscore‑Storage via SharedPreferences.【F:app/src/main/java/ch/logixisland/anuto/business/game/GameSaver.java†L24-L86】【F:app/src/main/java/ch/logixisland/anuto/business/game/HighScores.java†L1-L55】

---

## 📄 Lizenz

Siehe `LICENSE` im Repository.
