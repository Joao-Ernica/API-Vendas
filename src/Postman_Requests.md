# Postman_Requests.md

## Endpoints

### 1. Order

#### GET /payments/{status}
- **Description**: Fetch payments by status.
- **Path Variable**:
    - `status` (PaymentStatus): The status of the payments to fetch.
- **Response**:
  ```
  [
    {
      "id": 1,
      "amountPaid": 100.00,
      "data": "17-12-2024 14:44",
      "order": {
        "id": 101
      },
      "status": "PAGAMENTO_CONFIRMADO"
    }
  ]
