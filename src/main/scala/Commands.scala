import ch.ntb.usb.Device


sealed trait Command {
  def apply(code: Array[Byte], time: Int)(implicit device: Device): Unit = synchronized {
    device.controlMsg(0x21, 0x09, 0, 0, code, code.length, 2000, false)
    wait(time)
    Stop()
  }
}

object Stop extends Command {
  def apply()(implicit device: Device): Unit = {
    val command = Array[Byte](0x02, 0x20, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00)
    device.controlMsg(0x21, 0x09, 0, 0, command, command.length, 2000, false)
  }
}

object LedOn extends Command {
  def apply()(implicit device: Device): Unit =
    apply(Array[Byte](0x03, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00), 0)
}

object LedOff extends Command {
  def apply()(implicit device: Device): Unit =
    apply(Array[Byte](0x03, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00), 0)
}

object Up extends Command {
  def apply(time: Int)(implicit device: Device): Unit =
    apply(Array[Byte](0x02, 0x02, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00), time)
}

object Down extends Command {
  def apply(time: Int)(implicit device: Device): Unit =
    apply(Array[Byte](0x02, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00), time)
}

object Left extends Command {
  def apply(time: Int)(implicit device: Device): Unit =
    apply(Array[Byte](0x02, 0x04, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00), time)
}

object Right extends Command {
  def apply(time: Int)(implicit device: Device): Unit =
    apply(Array[Byte](0x02, 0x08, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00), time)
}

object Fire extends Command {
  def apply()(implicit device: Device): Unit =
    apply(Array[Byte](0x02, 0x10, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00), 4000)
}

object Zero extends Command {
  def apply()(implicit device: Device) = {
    Up(2200)
    Right(6000)
  }
}

object Mesfin extends Command {
  def apply()(implicit device: Device) = {
    Zero()
    Down(550)
    Left(4300)
  }
}

object James extends Command {
  def apply()(implicit device: Device) = {
    Zero()
    Down(400)
    Left(3190)
  }
}

object Karl extends Command {
  def apply()(implicit device: Device) = {
    Zero()
    Down(470)
    Left(1180)
  }
}

object David extends Command {
  def apply()(implicit device: Device) = {
    Zero()
    Down(380)
    Left(2100)
  }
}

object Romiro extends Command {
  def apply()(implicit device: Device) = {
    Zero()
    Down(315)
    Left(3620)
  }
}

object Joan extends Command {
  def apply()(implicit device: Device) = {
    Zero()
    Down(1100)
    Left(4020)
  }
}

object Ian extends Command {
  def apply()(implicit device: Device) = {
    Zero()
    Down(1100)
    Left(1370)
  }
}

object Lakshmi extends Command {
  def apply()(implicit device: Device) = {
    Zero()
    Down(450)
    Left(2670)
  }
}

object Stamos extends Command {
  def apply()(implicit device: Device) = {
    Zero()
    Down(200)
    Left(2720)
  }
}

object Karen extends Command {
  def apply()(implicit device: Device) = {
    Zero()
    Down(220)
    Left(5)
  }
}
