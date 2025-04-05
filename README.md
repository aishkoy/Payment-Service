# Payment API

## Description
Payment API is a REST API for processing financial transactions, managing user accounts, and performing administrative tasks in a payment system.

## Table of Contents
- [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
    - [User Operations](#user-operations)
    - [Transactions](#transactions)
    - [Accounts](#accounts)
    - [Administrative Functions](#administrative-functions)
- [Data Models](#data-models)
- [Response Status Codes](#response-status-codes)
- [Error Handling](#error-handling)
- [Examples](#examples)

## Installation

```bash
git clone https://github.com/aishkoy/Payment-Service.git
```

By default, the API will run on `http://localhost:8888`.

## Usage

The API provides the following core functionalities:
- User registration
- Creating and managing accounts in different currencies
- Executing transactions between accounts
- Viewing transaction history
- Administrative functions (approving transactions, rolling back transactions, blocking users)

## API Endpoints

### User Operations

#### Register User
- **URL**: `/api/register`
- **Method**: `POST`
- **Description**: Registers a new user in the system
- **Parameters**:
    - **Body**: [UserDto](#userdto)
- **Response**: ID of the created user (long)

### Transactions

#### Create Transaction
- **URL**: `/api/transactions`
- **Method**: `POST`
- **Description**: Creates a new transaction between accounts
- **Parameters**:
    - **Body**: [TransactionRequestDto](#transactionrequestdto)
- **Response**: ID of the created transaction (long)

#### Get Account Transaction History
- **URL**: `/api/transactions/{accountId}/history`
- **Method**: `GET`
- **Description**: Returns transaction history for the specified account
- **Parameters**:
    - **Path**: `accountId` - Account ID
- **Response**: Array of [TransactionDto](#transactiondto) objects

### Accounts

#### Create Account
- **URL**: `/api/accounts`
- **Method**: `POST`
- **Description**: Creates a new account in the specified currency
- **Parameters**:
    - **Body**: [CurrencyDto](#currencydto)
- **Response**: ID of the created account (long)

#### Get Accounts
- **URL**: `/api/accounts`
- **Method**: `GET`
- **Description**: Returns list of all user accounts
- **Response**: Array of [AccountDto](#accountdto) objects

#### Top Up Balance
- **URL**: `/api/accounts/balance`
- **Method**: `POST`
- **Description**: Adds funds to the specified account
- **Parameters**:
    - **Query**:
        - `accountId` - Account ID
        - `amount` - Amount to add
- **Response**: New account balance

#### Get Balance
- **URL**: `/api/accounts/balance`
- **Method**: `GET`
- **Description**: Returns current balance of the specified account
- **Parameters**:
    - **Query**: `accountId` - Account ID
- **Response**: Current account balance

### Administrative Functions

#### Block User
- **URL**: `/api/admin/users/{userId}`
- **Method**: `PUT`
- **Description**: Blocks the specified user
- **Parameters**:
    - **Path**: `userId` - User ID
- **Response**: Updated [UserDto](#userdto) object

#### Approve Transaction
- **URL**: `/api/admin/transactions/approval`
- **Method**: `POST`
- **Description**: Approves a transaction that is pending confirmation
- **Parameters**:
    - **Query**: `transactionId` - Transaction ID
- **Response**: Updated [TransactionDto](#transactiondto) object

#### Get Transactions Requiring Approval
- **URL**: `/api/admin/transactions/approval`
- **Method**: `GET`
- **Description**: Returns a list of transactions awaiting approval
- **Response**: Array of [TransactionDto](#transactiondto) objects

#### Rollback Transaction
- **URL**: `/api/admin/transactions/rollback`
- **Method**: `POST`
- **Description**: Cancels the specified transaction and returns funds
- **Parameters**:
    - **Query**: `transactionId` - Transaction ID
- **Response**: Updated [TransactionDto](#transactiondto) object

#### Delete Transaction
- **URL**: `/api/admin/transactions/{transactionId}`
- **Method**: `DELETE`
- **Description**: Removes the specified transaction from the system
- **Parameters**:
    - **Path**: `transactionId` - Transaction ID
- **Response**: Deleted [TransactionDto](#transactiondto) object

#### Get All Transactions
- **URL**: `/api/admin/transactions`
- **Method**: `GET`
- **Description**: Returns a list of all transactions in the system
- **Response**: Array of [TransactionDto](#transactiondto) objects

## Data Models

### UserDto
```json
{
  "name": "UserName",
  "phoneNumber": "996 (XXX) XX-XX-XX",
  "email": "user@example.com",
  "password": "Password123"
}
```

### TransactionRequestDto
```json
{
  "fromAccountId": 1,
  "toAccountId": 2,
  "amount": 1234334.12
}
```

### TransactionDto
```json
{
  "id": 1,
  "fromAccountId": 1,
  "toAccountId": 2,
  "amount": 3434124.32,
  "statusId": 1,
  "createdAt": "1985-09-25 17:45:30.005",
  "updatedAt": "1997-09-25 17:45:30.005"
}
```

### CurrencyDto
```json
{
  "currency": "USD"
}
```

### AccountDto
```json
{
  "id": 1,
  "userId": 1,
  "currencyId": 1,
  "balance": 3145135.12
}
```

## Response Status Codes

- **200 OK**: Request successful
- **400 Bad Request**: Invalid request or parameters
- **401 Unauthorized**: Authentication required
- **403 Forbidden**: Insufficient permissions to perform the operation
- **404 Not Found**: Requested resource not found
- **500 Internal Server Error**: Server-side error

## Error Handling

_Note: The provided API document does not specify error handling details. It is recommended to add information about the format of returned errors and their codes._

## Examples

### User Registration Example

**Request**:
```bash
curl -X POST http://localhost:8888/api/register \
  -H "Content-Type: application/json" \
  -d '{
      "name":"John",
      "phoneNumber":"996 (555) 55-55-55",
      "email":"john@example.com",
      "password":"1Securepassword"
      }'
```

**Response**:
```
1
```

### Account Creation Example

**Request**:
```bash
curl -X POST http://localhost:8888/api/accounts \
  -H "Content-Type: application/json" \
  -d '{"currency":"USD"}'
```

**Response**:
```
1
```

### Transaction Execution Example

**Request**:
```bash
curl -X POST http://localhost:8888/api/transactions \
  -H "Content-Type: application/json" \
  -d '{
      "fromAccountId":1,
      "toAccountId":2,
      "amount":10033.44
      }'
```

**Response**:
```
1
```

---

_Documentation created [06.04.2025]_