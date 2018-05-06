package controllers.builders

//権限の状態を持つのがこいつ

sealed trait UserRole
object UserRole {
 case object Admin  extends UserRole
 case object Viewer extends UserRole

}

case class Authority(role: Set[UserRole])
object Authority {
  def userRole(roles: UserRole*): Authority = Authority(Set(roles: _*))
}
