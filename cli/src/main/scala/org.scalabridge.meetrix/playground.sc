final case class LatLng(
                         lat: Double,
                         lng: Double
                       )




final case class ListCmdInput(
                               location: Option[LatLng],
                               radius: Option[Int]
                             )



val hhh = ListCmdInput(Option(LatLng(5.5,5.5)),Option(1))



hhh