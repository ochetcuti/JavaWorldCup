
# Java World Cup Simulation

This Java project brings a fictional World Cup to life, combining player and manager data to simulate matches, team formations, and tournament outcomes. The focus is on delivering a dynamic and data-driven tournament experience, blending Java’s object-oriented capabilities with team management.

## Table of Contents
- [Overview](#overview)
- [Key Features](#key-features)
- [Project Architecture](#project-architecture)
- [Data Handling & Processing](#data-handling--processing)
- [Team & Match Simulation](#team--match-simulation)
---

### Overview
This project provides an interactive experience of managing and simulating a global football tournament. Using Java, it builds teams from player and manager datasets, selects the strongest line-ups, and runs through the full tournament progression—from group stages to a final victor.

### Key Features
- **Comprehensive Data Loading**: Reads and processes player and manager data from CSV files, integrating them into squad structures with unique strengths and tactical preferences.
- **Player & Manager Models**: Creates detailed player and manager profiles, allowing for nuanced simulation of team strengths, weaknesses, and strategies.
- **Dynamic Team Formation**: Assembles match teams based on a manager's preferred formation, ensuring optimal lineup selection by analyzing player attributes.
- **Tournament Progression**: Simulates a classic World Cup format, with group and knockout stages, calculating match outcomes and printing real-time results to the console.

### Project Architecture
This project leverages Java’s object-oriented strengths with the following core classes:
- **Person, Player, and Manager**: These classes capture player and manager details, providing a foundation for team and game simulations.
- **Squad and Team**: These classes organize players and managers, structuring teams to participate in the tournament.
- **Main**: The control center that loads data, manages team selection, and runs the tournament flow.

### Data Handling & Processing
Using standard Java I/O, the project reads and parses CSV files to create player and manager objects, each with distinct attributes. Each dataset is processed line-by-line, ensuring accurate population of player skills and manager preferences, making it possible to simulate each team’s unique play style.

### Team & Match Simulation
The project allows for in-depth team formation based on manager-driven strategies. The team selection algorithm prioritizes the strongest players by role, balancing defense, midfield, and offense to match formation demands. Each match then simulates performance using the players’ individual attributes, advancing the top-performing teams through a World Cup-style tournament until a champion is crowned.
