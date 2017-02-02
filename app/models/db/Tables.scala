package models.db

// AUTO-GENERATED Slick data model [2017-02-02T12:10:21.689+03:00[Europe/Minsk]]

/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.PostgresDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.driver.PostgresDriver
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = PlayEvolutions.schema ++ Properties.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table PlayEvolutions
   *  @param id Database column id SqlType(int4), PrimaryKey
   *  @param hash Database column hash SqlType(varchar), Length(255,true)
   *  @param appliedAt Database column applied_at SqlType(timestamp)
   *  @param applyScript Database column apply_script SqlType(text), Default(None)
   *  @param revertScript Database column revert_script SqlType(text), Default(None)
   *  @param state Database column state SqlType(varchar), Length(255,true), Default(None)
   *  @param lastProblem Database column last_problem SqlType(text), Default(None) */
  case class PlayEvolutionsRow(id: Int, hash: String, appliedAt: java.time.OffsetDateTime, applyScript: Option[String] = None, revertScript: Option[String] = None, state: Option[String] = None, lastProblem: Option[String] = None)
  /** GetResult implicit for fetching PlayEvolutionsRow objects using plain SQL queries */
  implicit def GetResultPlayEvolutionsRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.time.OffsetDateTime], e3: GR[Option[String]]): GR[PlayEvolutionsRow] = GR{
    prs => import prs._
    PlayEvolutionsRow.tupled((<<[Int], <<[String], <<[java.time.OffsetDateTime], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table play_evolutions. Objects of this class serve as prototypes for rows in queries. */
  class PlayEvolutions(_tableTag: Tag) extends Table[PlayEvolutionsRow](_tableTag, "play_evolutions") {
    def * = (id, hash, appliedAt, applyScript, revertScript, state, lastProblem) <> (PlayEvolutionsRow.tupled, PlayEvolutionsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(hash), Rep.Some(appliedAt), applyScript, revertScript, state, lastProblem).shaped.<>({r=>import r._; _1.map(_=> PlayEvolutionsRow.tupled((_1.get, _2.get, _3.get, _4, _5, _6, _7)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(int4), PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.PrimaryKey)
    /** Database column hash SqlType(varchar), Length(255,true) */
    val hash: Rep[String] = column[String]("hash", O.Length(255,varying=true))
    /** Database column applied_at SqlType(timestamp) */
    val appliedAt: Rep[java.time.OffsetDateTime] = column[java.time.OffsetDateTime]("applied_at")
    /** Database column apply_script SqlType(text), Default(None) */
    val applyScript: Rep[Option[String]] = column[Option[String]]("apply_script", O.Default(None))
    /** Database column revert_script SqlType(text), Default(None) */
    val revertScript: Rep[Option[String]] = column[Option[String]]("revert_script", O.Default(None))
    /** Database column state SqlType(varchar), Length(255,true), Default(None) */
    val state: Rep[Option[String]] = column[Option[String]]("state", O.Length(255,varying=true), O.Default(None))
    /** Database column last_problem SqlType(text), Default(None) */
    val lastProblem: Rep[Option[String]] = column[Option[String]]("last_problem", O.Default(None))
  }
  /** Collection-like TableQuery object for table PlayEvolutions */
  lazy val PlayEvolutions = new TableQuery(tag => new PlayEvolutions(tag))

  /** Entity class storing rows of table Properties
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param keywords Database column keywords SqlType(_text), Length(2147483647,false)
   *  @param title Database column title SqlType(text)
   *  @param price Database column price SqlType(int4)
   *  @param propertyType Database column property_type SqlType(text)
   *  @param updatedInDays Database column updated_in_days SqlType(int2) */
  case class PropertiesRow(id: Int, keywords: List[String], title: String, price: Int, propertyType: String, updatedInDays: Short)
  /** GetResult implicit for fetching PropertiesRow objects using plain SQL queries */
  implicit def GetResultPropertiesRow(implicit e0: GR[Int], e1: GR[List[String]], e2: GR[String], e3: GR[Short]): GR[PropertiesRow] = GR{
    prs => import prs._
    PropertiesRow.tupled((<<[Int], <<[List[String]], <<[String], <<[Int], <<[String], <<[Short]))
  }
  /** Table description of table properties. Objects of this class serve as prototypes for rows in queries. */
  class Properties(_tableTag: Tag) extends Table[PropertiesRow](_tableTag, "properties") {
    def * = (id, keywords, title, price, propertyType, updatedInDays) <> (PropertiesRow.tupled, PropertiesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(keywords), Rep.Some(title), Rep.Some(price), Rep.Some(propertyType), Rep.Some(updatedInDays)).shaped.<>({r=>import r._; _1.map(_=> PropertiesRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column keywords SqlType(_text), Length(2147483647,false) */
    val keywords: Rep[List[String]] = column[List[String]]("keywords", O.Length(2147483647,varying=false))
    /** Database column title SqlType(text) */
    val title: Rep[String] = column[String]("title")
    /** Database column price SqlType(int4) */
    val price: Rep[Int] = column[Int]("price")
    /** Database column property_type SqlType(text) */
    val propertyType: Rep[String] = column[String]("property_type")
    /** Database column updated_in_days SqlType(int2) */
    val updatedInDays: Rep[Short] = column[Short]("updated_in_days")
  }
  /** Collection-like TableQuery object for table Properties */
  lazy val Properties = new TableQuery(tag => new Properties(tag))
}
