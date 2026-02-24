# 📍 AddressVerifier

A lightweight Spring Boot API that verifies New Zealand addresses against the **LINZ (Land Information New Zealand)** dataset using reactive programming with Project Reactor.

---

## 🚀 Features

- Verify addresses against the official LINZ address dataset
- Reactive, non-blocking architecture using `Mono` from Project Reactor
- Clean API with meaningful HTTP response codes
- Graceful error handling for unrecognised addresses

---

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Java | Core language |
| Spring Boot | Application framework |
| Spring WebFlux | Reactive REST API |
| Project Reactor | Reactive streams (`Mono`) |
| LINZ Data Service | Address verification source |

---

## 📡 API Reference

### Verify an Address

```
GET /api/addresses/verify?address={address}
```

**Query Parameters**

| Parameter | Type | Required | Description |
|---|---|---|---|
| `address` | `string` | ✅ | The address string to verify |

**Responses**

| Status Code | Description |
|---|---|
| `200 OK` | Address was found — returns a list of matching `Address` objects |
| `404 Not Found` | Address could not be found in the LINZ dataset |

**Example Request**

```bash
curl -X GET "http://localhost:8080/api/addresses/verify?address=1+Queen+Street+Auckland"
```

**Example Response (200 OK)**

```json
[
  {
    "fullAddress": "1 Queen Street, Auckland CBD, Auckland",
    "streetAddress": "1 Queen Street",
    "suburb": "Auckland CBD",
    "town": "Auckland"
  }
]
```

---

## 🏗️ Project Structure

```
src/
└── main/
    └── java/
        └── com/example/AddressVerifier/
            ├── client/
            │   └── LinzFeatureCollection
                └── LinzWfsClient
            ├── config/
            │   └── ApiKeyProperties
                └── CacheConfig
                └── WebClientConfig
            ├── security/
            │   └── KeyAuthenticationConverter
                └── KeyAuthenticationManager
                └── KeyAuthenticationToken
                └── SecurityConfig
            ├── controller/
            │   └── AddressController.java   # API endpoint definitions
                └── ResourceController.java  # Home endpoint definition
            ├── service/
            │   └── LinzAddressService.java  # Business logic & LINZ API calls
            ├── model/
            │   └── Address.java             # Address data model
            └── exception/
                └── AddressNotFoundException.java  # Custom exception
```

---

## ⚙️ Getting Started

### Prerequisites

- Java 17+
- Maven or Gradle
- A valid [LINZ Data Service API key](https://data.linz.govt.nz/)

### Configuration

Add the following to a .env file.

```properties
LINZ_API_KEY=your-linz-api-key
VALID_API_KEYS=a set of valid api keys for accessing the service, seperated by commas
ADMIN_PASSWORD=for accessing admin options (not currently implemented)

```

VALID_API_KEYS is used to validate the user if hosting the service on public server.

### Running the Application

```bash
# Clone the repository
git clone https://github.com/your-username/AddressVerifier.git
cd AddressVerifier

# Build and run with Maven
./gradlew bootRun
```

The API will be available at `http://localhost:8080`.

---

## 🔍 How It Works

1. A `GET` request is made to `/api/addresses/verify` with an address query parameter.
2. The `AddressController` delegates to `LinzAddressService`, which queries the LINZ dataset reactively.
3. If one or more matching addresses are found, they are returned as a list with a `200 OK` response.
4. If no match is found, an `AddressNotFoundException` is thrown and caught, returning a clean `404 Not Found`.

---

## 📄 License

This project is licensed under the [GNU General Public License v3.0](COPYING.txt)

