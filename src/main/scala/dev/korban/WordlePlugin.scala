package dev.korban.wordle

import dotty.tools.dotc.ast.Trees.*
import dotty.tools.dotc.ast.tpd
import dotty.tools.dotc.core.Constants.Constant
import dotty.tools.dotc.core.Contexts.Context
import dotty.tools.dotc.core.Decorators.*
import dotty.tools.dotc.core.StdNames.*
import dotty.tools.dotc.core.Symbols.*
import dotty.tools.dotc.plugins.{PluginPhase, StandardPlugin}
import dotty.tools.dotc.transform.{Pickler, Staging}
import dotty.tools.dotc.report
import dotty.tools.dotc.core.Contexts.FreshContext
import dotty.tools.dotc.reporting.NoExplanation

class WordlePlugin extends StandardPlugin:
  val name: String = "wordleCompile"
  override val description: String = "Requires the user to solve a wordle"

  def init(options: List[String]): List[PluginPhase] =
    (new WordlePhase) :: Nil

class WordlePhase extends PluginPhase:
  import tpd.*

  val phaseName = "wordleCompile"

  override val runsAfter = Set(Pickler.name)
  override val runsBefore = Set(Staging.name)

  override def initContext(ctx: FreshContext): Unit =
    if !Wordle.finishWordle() then
      report.error(NoExplanation("Come on man, you have to finish a wordle!"))(using ctx)

end WordlePhase
