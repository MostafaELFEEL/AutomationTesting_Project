# 🚀 Enterprise E-Commerce Automation Framework

A highly scalable, thread-safe End-to-End (E2E) UI Automation Framework built to test complex e-commerce workflows (Cart, Checkout, Authentication, Product Discovery). 

Engineered for extreme performance, this framework utilizes strictly isolated `ThreadLocal` instances to run **41 complex E2E tests across 12 concurrent threads**, slashing execution time by **88%** (from 25 minutes down to just 3 minutes).

## 📊 Performance Benchmark
* **Total Tests:** 41 E2E Workflows
* **Concurrency:** 12 Threads (Maximized hardware utilization)
* **Sequential Execution Time:** ~25 minutes
* **Parallel Execution Time:** ~3 minutes

## 🛠 Tech Stack
* **Language:** Java 25 (LTS)
* **Browser Automation:** Selenium WebDriver (v4.x)
* **Testing Framework:** TestNG
* **Build Tool:** Apache Maven
* **Design Pattern:** Strict Page Object Model (POM)
* **Data Parsing:** Gson (JSON to POJO Data-Driven Testing)
* **Live Observability:** ExtentReports
* **Historical Reporting:** Allure Reports

## 🏗 Architectural Highlights
* **True Parallel Execution:** WebDriver and Page Object instances are wrapped in `ThreadLocal` to ensure zero data bleeding, session collisions, or `NullPointerExceptions` during heavy multi-threaded execution.
* **Flakiness Eradicated:** Eliminated static `Thread.sleep()` in favor of smart, timeout-based waits. Includes a custom TestNG `RetryAnalyzer` to automatically catch and rescue UI rendering timeouts.
* **Centralized Configuration:** Driven by a custom `ConfigLoader` parsing a `config.properties` file for plug-and-play browser selection and environment management.
* **Dual-Reporting Strategy:** * *ExtentReports:* Triggered via a custom `ITestListener` for real-time, live-updating HTML dashboards during execution.
  * *Allure:* Granular, step-by-step historical tracking with automated screenshot attachments on test failure.

## 🚀 Getting Started

### Prerequisites
* Java JDK 25+
* Apache Maven
* Chrome / Edge Browser installed
* Allure installed

### Installation
Clone the repository:
```bash
git clone [https://github.com/MostafaELFEEL/AutomationTesting_Project.git](https://github.com/MostafaELFEEL/AutomationTesting_Project.git)
cd AutomationTesting_Project
```
### Configuration
Adjust the execution settings (Project Path, browser type, headless mode, etc.) in the config file located at:
src/test/resources/config.properties

### Execution
To run the entire suite in parallel via Maven:

```Bash
mvn clean test
```
To run via TestNG XML:
Right-click tests.xml in your IDE and select Run. (Configure your <suite thread-count="12"> inside this file to match your machine's CPU cores for optimal performance).

### 📈 Viewing the Reports
1. Live Dashboard (ExtentReports)
Navigate to the /target directory after execution begins and open LiveTestDashboard.html in any browser to watch the tests execute in real-time.

2. Step-by-Step Dashboard (Allure)
Once the test suite finishes, generate and launch the Allure report by running:

```Bash
allure serve <allure-results folder path>
```
### 👨‍💻 Author
Mostafa Ashraf Ibrahim Ali El-Feel Connect with me on LinkedIn to discuss QA architecture, parallel execution, and test observability!
