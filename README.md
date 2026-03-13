# 🚀 Enterprise E-Commerce Automation Framework

A highly scalable, thread-safe End-to-End (E2E) Hybrid Automation Framework built to test complex e-commerce workflows (Cart, Checkout, Authentication, Product Discovery) and backend API services. 

Engineered for extreme performance, this framework utilizes strictly isolated `ThreadLocal` instances to run **61 complex tests (41 E2E UI + 20 API) across 12 concurrent threads**, slashing execution time by **88%** (from 25 minutes down to just 3 minutes).

## 📊 Performance Benchmark
* **Total Tests:** 61 (41 E2E UI Workflows + 20 API Endpoints)
* **Concurrency:** 12 Threads (Maximized hardware utilization)
* **Sequential Execution Time:** ~25 minutes
* **Parallel Execution Time:** ~3 minutes

## 🛠 Tech Stack
* **Language:** Java 25 
* **UI Automation:** Selenium WebDriver (v4.x)
* **API Automation:** REST Assured
* **Testing Framework:** TestNG (Configured via `tests.xml` Suite)
* **Build Tool:** Apache Maven
* **Design Pattern:** Strict Page Object Model (POM)
* **Data-Driven Testing:** TestNG `DataProvider` & Gson (Parsing JSON arrays to POJOs)
* **Resilience & Tracking:** Custom `ITestListener` & `RetryAnalyzer`
* **Reporting:** Allure Reports

## 🏗 Architectural Highlights
* **Custom-Built Framework:** Designed and developed the entire automation architecture from the ground up to serve as a robust, scalable foundation for enterprise QA.
* **Zero-Config Portability:** Dynamic pathing.
* **Hybrid Execution Flow:** Seamless integration of UI and API test execution. API tests execute stateful, data-driven CRUD lifecycles in strict priority before releasing 12 parallel threads for the UI suite to prevent data collisions.
* **True Parallel Execution:** WebDriver and Page Object instances are wrapped in `ThreadLocal` to ensure zero data bleeding, session collisions, or `NullPointerExceptions` during heavy multi-threaded execution.
* **Flakiness Eradicated:** Eliminated static `Thread.sleep()` in favor of smart, timeout-based waits. Includes a custom TestNG `RetryAnalyzer` to automatically catch and rescue UI rendering timeouts.
* **Centralized Configuration:** Driven by a custom `ConfigLoader` parsing a `config.properties` file for plug-and-play browser selection, headless modes, and environment management.
* **Comprehensive Reporting:** Automated, step-by-step historical tracking using **Allure**, including automatic HTML-stripping for API payloads and automated screenshot attachments for UI test failures.

## 🚀 Getting Started

### Prerequisites
* Java JDK 25+
* Apache Maven
* Chrome / Edge Browser installed
* Allure Commandline installed

### Installation
Clone the repository:
```bash
git clone https://github.com/MostafaELFEEL/AutomationTesting_Project.git

```

### Configuration

Adjust the execution settings (browser type, headless mode, environment setup, etc.) in the config file located at:
`config.properties`

### Execution

**To run via TestNG XML:**
Right-click `tests.xml` in your IDE and select **Run**. (Configure your `<suite thread-count="12">` inside this file to match your machine's CPU cores for optimal performance).


## 📈 Viewing the Reports

**Step-by-Step Dashboard (Allure)**
Once the test suite finishes executing, generate and launch the Allure dashboard by running the following command in your terminal:

```bash
allure serve <allure-results folder path>

```

## 👨‍💻 Author

**Mostafa Ashraf Ibrahim Ali El-Feel** Connect with me on [LinkedIn](https://www.linkedin.com/in/mostafa-ashraf-elfeel/) to discuss QA architecture, parallel execution, and test observability!
