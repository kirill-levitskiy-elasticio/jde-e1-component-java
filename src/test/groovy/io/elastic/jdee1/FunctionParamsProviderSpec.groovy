package io.elastic.jdee1

import spock.lang.*

import javax.json.Json
import javax.json.JsonObject
import javax.json.JsonObjectBuilder

@Ignore
class FunctionParamsProviderSpec extends Specification {

  JsonObjectBuilder configuration = Json.createObjectBuilder()
  @Shared
  def server = "JDE92ENT"
  @Shared
  def port = "6017"
  @Shared
  def user = "JDE"
  @Shared
  def password = "jde"
  @Shared
  def environment = "JDV920"
  @Shared
  def function = "AddressBookMasterMBF"

  def setup() {

  }

  def "get metadata model, given json object"() {
    configuration.add("server", server)
    .add("port", port)
    .add("user", user)
    .add("pwd", password)
    .add("environment", environment)
    .add("name", function)

    FunctionParamsProvider provider = new FunctionParamsProvider()
    JsonObject meta = provider.getMetaModel(configuration.build())
    print meta
    expect:
    meta.toString() == "{\"out\":{\"type\":\"object\",\"properties\":{\"watched\":{\"title\":\"watched\",\"type\":\"boolean\"},\"created\":{\"title\":\"created\",\"type\":\"date\"}}},\"in\":{\"type\":\"object\",\"properties\":{\"watched\":{\"title\":\"watched\",\"type\":\"boolean\"},\"created\":{\"title\":\"created\",\"type\":\"date\"}}}}"
  }
}