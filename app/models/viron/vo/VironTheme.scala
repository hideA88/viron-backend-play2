package models.viron.vo

sealed abstract class VironTheme(val value: String)

object VironTheme {
  case object Standard extends VironTheme("standard")
}
