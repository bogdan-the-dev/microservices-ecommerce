export enum PaymentType {
  CARD,
  WIRE_TRANSFER,
  CASH_DELIVERY
}

export function getPaymentTypeByValue(enumValue: any): string | undefined {
  for (const key of Object.keys(PaymentType)) {
    if (PaymentType[key] === enumValue) {
      return key;
    }
  }
  return undefined;
}
