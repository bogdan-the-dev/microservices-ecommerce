
export class Serializer{
  public static serialize(data: Map<string, Map<string, string>>) {
    const jsonObject: { [key: string]: { [key: string]: string } } = {};
    data.forEach((innerMap, key) => {
      jsonObject[key] = {};
      innerMap.forEach((value, innerKey) => {
        jsonObject[key][innerKey] = value;
      });
    });

    return  JSON.stringify(jsonObject)
  }
}
