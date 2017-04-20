package services

package object nestoria {

  sealed trait Parameter

  sealed trait Country

  case object Australia extends Country
  case object Brazil extends Country
  case object UK extends Country

  def stringify(c: Country): String = c match {
    case Australia => ".com.eu"
    case Brazil => ".com.br"
    case UK => ".co.uk"
  }

  sealed trait Action

  case object Echo extends Action
  case object Keywords extends Action
  case object Metadata extends Action
  case object SearchListings extends Action

  sealed trait Filter extends Parameter

  case class ListingType(tpe: String) extends Filter

  def stringify(a: Action): String = a match {
    case Echo => "echo"
    case Keywords => "keywords"
    case Metadata => "metadata"
    case SearchListings => "search_listings"
  }

  def stringify(param: Parameter): (String, String) = param match {
    case ListingType(tpe) => ("listing_type", tpe)
  }

  private[nestoria] implicit class UrlString(heads: String) {
    def &(tails: String) = heads + "&" + tails
  }
}
