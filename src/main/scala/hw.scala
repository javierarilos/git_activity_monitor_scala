import scalaj.http._
import org.json4s._
import org.json4s.jackson.JsonMethods.parse

object Hi {

  /**
    * GITHUB PUBLIC EVENTS API
    *
    * Header: 'If-None-Match: "{previous-ETAG}"' must be set to get NEW activity from the previous request.
    $ curl -v  https://api.github.com/orgs/Telefonica/events -H 'If-None-Match: "f4bb3acf59d01bbf9a92ad510eeb51eb"'
    *
    * status codes:
    < HTTP/1.1 200 OK
    < HTTP/1.1 304 Not Modified
    *
    * headers:
    < X-RateLimit-Limit: 60
    < X-RateLimit-Remaining: 53
    < ETag: "f36ac2f932dbb3dce78b9770b8b9b035"
    < X-Poll-Interval: 60
    < X-GitHub-Media-Type: github.v3
    < Link: <https://api.github.com/organizations/1536176/events?page=2>; rel="next", <https://api.github.com/organizations/1536176/events?page=10>; rel="last"
    < Access-Control-Expose-Headers: ETag, Link, X-GitHub-OTP, X-RateLimit-Limit, X-RateLimit-Remaining, X-RateLimit-Reset, X-OAuth-Scopes, X-Accepted-OAuth-Scopes, X-Poll-Interval
    *
    * ***** Preparing the response summary:
    * ** common fields
    * id
    * type
    * actor.display_login
    * actor.avatar_url
    * repo.name
    * created_at (ISO8601 date-time)
    * org.login
    * org.avatar_url
    *
    * Event types:
    *     "type": "DeleteEvent",
    *     "type": "CreateEvent",
    *     "type": "ReleaseEvent",
    *     "type": "IssuesEvent",


    */

  def getOrganizationEvents(org: String): HttpResponse[String] = {
    Http("https://api.github.com/orgs/"+org+"/events").method("GET").asString
  }

  def getLinks(gitHubResp: HttpResponse[String]) = {
    val links = gitHubResp.headers("Link")(0).split(",")
    (links(0), links(1))
  }

  def asJson(json: String) = parse(json)

  def main(args: Array[String]) = {
    println("Hi!")
    val response = getOrganizationEvents("Telefonica")

    println(">>>>>>>>>> "+response.headers)
    println()
    println("b:::"+getLinks(response).toString())
    println()
    println()
    implicit val formats = org.json4s.DefaultFormats
    //println(parse(response.body).extract[Map[String, Any]])

    val activity = asJson(response.body)

    println(activity(0))
    println()
    println()
    println(activity(1))


  }
}
