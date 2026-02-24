# 📍 AddressVerifier

A lightweight Spring Boot API that verifies New Zealand addresses against the **LINZ (Land Information New Zealand)** dataset using reactive programming with Project Reactor.

**Contact:** [hamish@phillipsmusictech.co.nz](mailto:hamish@phillipsmusictech.co.nz)
**Production URL:** [https://address-verifier.phillipsmusictech.co.nz](https://address-verifier.phillipsmusictech.co.nz)

---

## 🚀 Features

- Verify addresses against the official LINZ address dataset
- Case-insensitive prefix search across all NZ addresses
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

**Base URL:** `https://address-verifier.phillipsmusictech.co.nz`

### Authentication

All endpoints require an API key passed in the request header.

| Header | Description |
|---|---|
| `X-Api-Key` | Your API key — required on every request |

Requests without a valid key will receive a `401 Unauthorized` response.

---

### Verify an Address

```
GET /addresses/verify?address={address}
```

Performs a **case-insensitive prefix search** against LINZ address data, returning all matching addresses.

**Query Parameters**

| Parameter | Type | Required | Min Length | Description |
|---|---|---|---|---|
| `address` | `string` | ✅ | 3 characters | The address string to search for |

**Responses**

| Status Code | Description |
|---|---|
| `200 OK` | One or more matching addresses found |
| `401 Unauthorized` | Missing or invalid API key |
| `404 Not Found` | No matching address found in the LINZ dataset |
| `500 Internal Server Error` | An unexpected server error occurred |

**Example Request**

```bash
curl -X GET "https://address-verifier.phillipsmusictech.co.nz/addresses/verify?address=123+George+Street" \
  -H "X-Api-Key: your_api_key_here"
```

**Example Response (200 OK)**

```json
[
  {
    "fullAddress": "123 George Street, Central Dunedin, Dunedin 9016",
    "streetAddress": "123 George Street",
    "suburb": "Central Dunedin",
    "town": "Dunedin"
  }
]
```

**Example Error Response (401 / 404 / 500)**

```json
{
  "message": "Address not found",
  "status": 404,
  "timestamp": "2026-01-28T09:42:00Z"
}
```

---

### Address Schema

| Field | Type | Description | Example |
|---|---|---|---|
| `fullAddress` | `string` | Full formatted address | `123 George Street, Central Dunedin, Dunedin 9016` |
| `streetAddress` | `string` | Street address component | `123 George Street` |
| `suburb` | `string` | Suburb or locality | `Central Dunedin` |
| `town` | `string` | Town or city | `Dunedin` |

### Error Response Schema

| Field | Type | Required | Description | Example |
|---|---|---|---|---|
| `message` | `string` | ✅ | Human-readable error message | `Address not found` |
| `status` | `integer` | ✅ | HTTP status code | `404` |
| `timestamp` | `string` | ❌ | ISO 8601 datetime of the error | `2026-01-28T09:42:00Z` |

---

### OpenAPI Specification

The API specification can be found [here](/Documentation/AddressVerifier.yaml)

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

VALID_API_KEYS is used to validate the user if hosting the service on a public server.

### Running the Application

```bash
# Clone the repository
git clone https://github.com/your-username/AddressVerifier.git
cd AddressVerifier

# Build and run with Gradle
./gradlew bootRun
```

The API will be available at `http://localhost:8080`.

---

## 🔍 How It Works

1. A `GET` request is made to `/addresses/verify` with an address query parameter and a valid `X-Api-Key` header.
2. The `AddressController` delegates to `LinzAddressService`, which performs a case-insensitive prefix search against the LINZ dataset reactively.
3. If one or more matching addresses are found, they are returned as a list with a `200 OK` response.
4. If no match is found, an `AddressNotFoundException` is thrown and caught, returning a clean `404 Not Found` with a structured error body.

---

## 📄 License

This project is licensed under the [GNU General Public License v3.0](COPYING.txt)
