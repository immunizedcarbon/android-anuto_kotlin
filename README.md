# Anuto (Android) – Tower Defense Engine & Game

![Anuto Logo](images/icon.png)

**Anuto** ist ein vollständig eigenständiges Tower‑Defense‑Spiel für Android mit eigener Engine, eigener Render‑Pipeline und einer klar getrennten Architektur aus **Engine**, **Gameplay‑Logik** und **UI**. Der Fokus liegt auf **flüssiger Darstellung**, **klarer Spielmechanik** und **datengetriebenen Inhalten**.

[![Platform](https://img.shields.io/badge/platform-Android-3ddc84?logo=android&logoColor=white)](#) 
[![Language](https://img.shields.io/badge/language-Kotlin%20%2B%20Java-7f52ff?logo=kotlin&logoColor=white)](#) 
[![Build](https://img.shields.io/badge/build-Gradle-02303a?logo=gradle&logoColor=white)](#)

---

## ✨ Highlights auf einen Blick

- **Eigene Engine statt Standard‑Framework**: Game‑Loop, Renderer, Audio und Theme‑Handling sind bewusst separat aufgebaut.
- **Datengetriebene Inhalte**: Karten und Wellen werden als JSON geladen – perfekt erweiterbar ohne Engine‑Änderung.
- **Modernes Gameplay**: Wellen‑System mit Boni, Tower‑Aging, Target‑Lock und vielfältigen Tower‑Rollen.
- **Persistenter Fortschritt**: Autosaves, manuelle Saves inkl. Screenshot, Migration und Highscores pro Karte.

---

## 🖼️ Eindrücke

| Gameplay | Kartenübersicht |
| --- | --- |
| ![Gameplay Screenshot](images/screenshot1.png) | ![Map Screenshot](images/screenshot2.png) |

---

## 📚 Inhaltsverzeichnis

- [Spielprinzip](#-spielprinzip)
- [Funktionen](#-funktionen)
- [Steuerung & UI](#-steuerung--ui)
- [Inhalte & Datenformate](#-inhalte--datenformate)
- [Architektur](#-architektur)
- [Erweiterungspunkte](#-erweiterungspunkte)
- [Build & Run](#-build--run)
- [Lizenz](#-lizenz)

---

## 🎮 Spielprinzip

- **Wellenbasiertes Tower‑Defense**: Gegner erscheinen in Wellen, die dynamisch auf deine Leistung reagieren.
- **Ökonomie & Strategie**: Credits, Lives und Bonus‑Mechaniken fordern langfristige Planung.
- **Taktische Vielfalt**: Kombiniere Tower‑Typen (z. B. Laser, Kanonen, Minen, Raketen) für Synergien.

---

## 🧩 Funktionen

### Gameplay
- **Wellen‑System mit Boni & dynamischer Schwierigkeit**
- **Tower‑Upgrade & Spezialisierung** (Fokus, Verstärken, Strategiewechsel)
- **Gegnervielfalt** (u. a. Flyer, Healer, Sprinter, Soldier)
- **Tutorial‑Flow** mit kontextbezogenen Hinweisen (deaktivierbar)

### Fortschritt & Persistenz
- **Autosave + manuelles Speichern** inkl. Screenshot
- **Highscores pro Karte**
- **Migration alter Spielstände**

### Audio & Darstellung
- **Canvas‑Renderer mit Viewport‑Mapping**
- **Sound‑Management & Theme‑Switching**

---

## 🕹 Steuerung & UI

- **Drag & Drop** für Tower‑Placement
- **Kontextmenüs** für Upgrades und Target‑Lock
- **In‑Game HUD** mit Lives, Credits, Wellenstatus und Bonus‑Infos

---

## 🗺 Inhalte & Datenformate

- **Karten**: JSON‑Definitionen in `res/raw` (z. B. `map_original.json`)
- **Wellen**: zentrale Wellenliste in `res/raw/waves.json`
- **Audio**: Soundeffekte als `.ogg` in `res/raw`

---

## 🏗 Architektur

**Schichtenmodell**

1. **Engine‑Layer (`engine/*`)**
   - Game‑Loop, Renderer, Audio, Theme‑Management
2. **Business‑Layer (`business/*`)**
   - Spielzustand, Wellenlogik, Speichern/Laden, Highscores
3. **Entity‑Layer (`entity/*`)**
   - Towers, Enemies, Shots, Effects
4. **UI‑Layer (`view/*`)**
   - GameActivity, GameView, Input‑Handling

---

## 🧱 Erweiterungspunkte

- **Neue Maps**: JSON in `res/raw` ablegen und in `MapRepository` registrieren
- **Neue Entities**: Factory & Persister hinzufügen, Registrierung in `GameFactory`
- **Neue Wellen**: `waves.json` erweitern – Parsing erfolgt automatisch

---

## 🚀 Build & Run

**Voraussetzungen**
- Android Studio (aktuelles Stable‑Release)
- JDK 17
- Android SDK 36 (compileSdk/targetSdk)

**Build**
```bash
./gradlew assembleDebug
```

**Install auf Gerät/Emulator**
```bash
./gradlew installDebug
```

---

## 📄 Lizenz

Siehe `LICENSE` im Repository.
