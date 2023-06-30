export interface ProductFilerModel {
  fieldName: string
  comparisonType: ComparisonType
  value: string
}

export enum ComparisonType {
  EQUALS,
  GREATER_THAN,
  LESS_THAN,
  CONTAINS
}
