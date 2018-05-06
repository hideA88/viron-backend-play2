package models.base

trait EnumLike {
  type Value
  def value: Value
}

trait StringEnumLike extends EnumLike {
  override type Value = String

}
