package eu.fakod.sjersey.inject.specs

import eu.fakod.sjersey.inject.ScalaCollectionQueryParamInjectable
import com.sun.jersey.server.impl.model.parameter.multivalued.MultivaluedParameterExtractor
import com.sun.jersey.api.core.{ExtendedUriInfo, HttpContext}
import javax.ws.rs.core.MultivaluedMap
import eu.fakod.sjersey.SJerseyTestBase

class ScalaCollectionQueryParamInjectableSpec extends SJerseyTestBase {
  // TODO: Aug 17, 2010 <coda> -- test error handling

  val extractor = mock[MultivaluedParameterExtractor]
  val context = mock[HttpContext]
  val uriInfo = mock[ExtendedUriInfo]
  val params = mock[MultivaluedMap[String, String]]
  val extracted = mock[Object]

  extractor.extract(params) returns extracted
  context.getUriInfo returns uriInfo

  "A Scala collection query param injectable with decoding" should {
    val injectable = new ScalaCollectionQueryParamInjectable(extractor, true)
    uriInfo.getQueryParameters(true) returns params

    "extracts the query parameters" in {
      val e = injectable.getValue(context)

      e.must(be(extracted))
    }
  }

  "A Scala collection query param injectable without decoding" should {
    val injectable = new ScalaCollectionQueryParamInjectable(extractor, false)
    uriInfo.getQueryParameters(false) returns params

    "extracts the query parameters" in {
      val e = injectable.getValue(context)

      e.must(be(extracted))
    }
  }
}
