export class Deserializer {
  static deserialize(input: string) {
    const parsedObject = JSON.parse(input);

    // Transform the JavaScript object into a Map
    let res = new Map<string, Map<string, string>>();

    for (const key of Object.keys(parsedObject)) {
      const innerObject = parsedObject[key];
      const innerMap = new Map<string, string>();

      for (const innerKey of Object.keys(innerObject)) {
        innerMap.set(innerKey, innerObject[innerKey]);
      }

      res.set(key, innerMap);
    }
    return res
  }
}
