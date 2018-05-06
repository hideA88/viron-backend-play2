package utils

import org.slf4j
import play.api.Logger

trait LoggerProvider {
  private lazy val _logger = Logger(getClass)

  protected def logger: slf4j.Logger = _logger.logger
}
