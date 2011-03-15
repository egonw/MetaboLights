// This file was autogenerated by /home/markr/src/metabolomes/trunk/src/main/javascript/specview/third-party/closure/closure/bin/build/depswriter.py.
// Please do not edit.
goog.addDependency('../../../../specview/controller/command.js', ['specview.controller.Command'], []);
goog.addDependency('../../../../specview/controller/controller.js', ['specview.controller.Controller', 'specview.controller.Controller.EventType'], ['goog.async.Delay', 'goog.debug.Console', 'goog.editor.BrowserFeature', 'goog.events', 'goog.graphics', 'goog.ui.KeyboardShortcutHandler', 'goog.ui.Prompt', 'specview.controller.Plugin', 'specview.model.NeighborList', 'specview.view.MoleculeRenderer']);
goog.addDependency('../../../../specview/controller/plugin.js', ['specview.controller.Plugin'], ['goog.debug.Logger', 'goog.events.EventTarget', 'goog.functions', 'goog.object', 'goog.reflect']);
goog.addDependency('../../../../specview/controller/plugins/highlight.js', ['specview.controller.plugins.Highlight'], ['goog.debug.Logger', 'specview.controller.Plugin']);
goog.addDependency('../../../../specview/deploy/specview.js', [], []);
goog.addDependency('../../../../specview/deps.js', [], []);
goog.addDependency('../../../../specview/graphics/affine_transform.js', ['specview.graphics.AffineTransform'], ['goog.graphics.AffineTransform']);
goog.addDependency('../../../../specview/graphics/element_array.js', ['specview.graphics.ElementArray'], ['goog.array', 'goog.debug.Logger']);
goog.addDependency('../../../../specview/io/json.js', ['specview.io.json'], ['goog.array', 'goog.json', 'goog.math.Coordinate', 'specview.model.Atom', 'specview.model.Bond', 'specview.model.Molecule']);
goog.addDependency('../../../../specview/io/mdl.js', ['specview.io.mdl'], ['goog.debug.Logger', 'goog.i18n.DateTimeFormat', 'goog.string', 'specview.model.Atom', 'specview.model.Bond', 'specview.model.Molecule']);
goog.addDependency('../../../../specview/json/json_templates.js', [], []);
goog.addDependency('../../../../specview/math/line.js', ['specview.math.Line'], ['goog.math.Coordinate', 'specview.math.Triangle']);
goog.addDependency('../../../../specview/math/triangle.js', ['specview.math.Triangle'], []);
goog.addDependency('../../../../specview/model/atom.js', ['specview.model.Atom', 'specview.model.Atom.Hybridizations'], ['goog.debug.Logger', 'goog.math.Coordinate', 'goog.structs.Set', 'specview.model.Flags', 'specview.resource.ImplicitHydrogens']);
goog.addDependency('../../../../specview/model/bond.js', ['specview.model.Bond'], ['specview.model.Atom']);
goog.addDependency('../../../../specview/model/flags.js', ['specview.model.Flags'], []);
goog.addDependency('../../../../specview/model/molecule.js', ['specview.model.Molecule'], ['goog.array', 'goog.debug.Logger', 'goog.math.Vec2', 'specview.graphics.AffineTransform', 'specview.model.Atom', 'specview.ring.RingFinder']);
goog.addDependency('../../../../specview/model/neighborlist.js', ['specview.model.NeighborList'], ['goog.array', 'goog.math.Line', 'goog.math.Vec2']);
goog.addDependency('../../../../specview/model/pseudo_atom.js', ['specview.model.PseudoAtom'], ['specview.model.Atom']);
goog.addDependency('../../../../specview/resource/covalence.js', ['specview.resource.Covalence'], []);
goog.addDependency('../../../../specview/resource/elements.js', ['specview.resource.Elements'], []);
goog.addDependency('../../../../specview/resource/implicith.js', ['specview.resource.ImplicitHydrogens'], []);
goog.addDependency('../../../../specview/ring/alpha-pinene.js', [], []);
goog.addDependency('../../../../specview/ring/azulene.js', [], []);
goog.addDependency('../../../../specview/ring/biphenyl.js', [], []);
goog.addDependency('../../../../specview/ring/hanser.js', ['specview.ring.Hanser'], ['goog.array', 'goog.structs.Set', 'specview.ring.PathEdge', 'specview.ring.PathGraph', 'specview.ring.Ring']);
goog.addDependency('../../../../specview/ring/path_edge.js', ['specview.ring.PathEdge'], []);
goog.addDependency('../../../../specview/ring/path_graph.js', ['specview.ring.PathGraph'], []);
goog.addDependency('../../../../specview/ring/ring.js', ['specview.ring.Ring'], ['goog.array', 'goog.memoize', 'goog.structs.Map', 'specview.model.Flags']);
goog.addDependency('../../../../specview/ring/ring_finder.js', ['specview.ring.RingFinder'], ['goog.array', 'goog.structs.Set', 'specview.ring.Hanser', 'specview.ring.Ring', 'specview.ring.SSSR']);
goog.addDependency('../../../../specview/ring/ring_partitioner.js', ['specview.ring.RingPartitioner'], ['goog.array']);
goog.addDependency('../../../../specview/ring/spiro_decane.js', [], []);
goog.addDependency('../../../../specview/ring/sssr.js', ['specview.ring.SSSR'], ['goog.array', 'goog.structs.Set', 'specview.ring.Ring']);
goog.addDependency('../../../../specview/util/bond_util.js', ['specview.util.BondUtil', 'specview.util.BondUtil.Orders'], ['specview.model.Atom', 'specview.model.Bond', 'specview.model.PseudoAtom']);
goog.addDependency('../../../../specview/view/aromaticity_renderer.js', ['specview.view.AromaticityRenderer'], ['specview.view.BondRenderer']);
goog.addDependency('../../../../specview/view/atom_renderer.js', ['specview.view.AtomRenderer'], ['goog.debug.Logger', 'specview.graphics.ElementArray', 'specview.view.Renderer']);
goog.addDependency('../../../../specview/view/bond_renderer.js', ['specview.view.BondRenderer'], ['specview.math.Line', 'specview.view.Renderer']);
goog.addDependency('../../../../specview/view/bond_renderer_factory.js', ['specview.view.BondRendererFactory'], ['goog.object', 'goog.reflect', 'specview.model.Bond', 'specview.view.DoubleBondRenderer', 'specview.view.QuadrupleBondRenderer', 'specview.view.SingleBondRenderer', 'specview.view.SingleDownBondRenderer', 'specview.view.SingleUpBondRenderer', 'specview.view.SingleUpOrDownBondRenderer', 'specview.view.TripleBondRenderer']);
goog.addDependency('../../../../specview/view/double_bond_renderer.js', ['specview.view.DoubleBondRenderer'], ['specview.view.BondRenderer']);
goog.addDependency('../../../../specview/view/molecule_renderer.js', ['specview.view.MoleculeRenderer'], ['goog.asserts', 'specview.graphics.ElementArray', 'specview.view.AromaticityRenderer', 'specview.view.AtomRenderer', 'specview.view.BondRenderer', 'specview.view.BondRendererFactory']);
goog.addDependency('../../../../specview/view/quadruple_bond_renderer.js', ['specview.view.QuadrupleBondRenderer'], ['specview.view.BondRenderer']);
goog.addDependency('../../../../specview/view/renderer.js', ['specview.view.Renderer'], ['goog.debug.Logger', 'goog.structs.Map']);
goog.addDependency('../../../../specview/view/single_bond_renderer.js', ['specview.view.SingleBondRenderer'], ['goog.math.Coordinate', 'goog.math.Vec2', 'specview.view.BondRenderer']);
goog.addDependency('../../../../specview/view/single_down_bond_renderer.js', ['specview.view.SingleDownBondRenderer'], ['specview.view.BondRenderer']);
goog.addDependency('../../../../specview/view/single_up_bond_renderer.js', ['specview.view.SingleUpBondRenderer'], ['specview.view.BondRenderer']);
goog.addDependency('../../../../specview/view/single_up_or_down_bond_renderer.js', ['specview.view.SingleUpOrDownBondRenderer'], ['goog.debug', 'goog.debug.FancyWindow', 'goog.debug.Logger', 'specview.view.BondRenderer']);
goog.addDependency('../../../../specview/view/triple_bond_renderer.js', ['specview.view.TripleBondRenderer'], ['specview.view.BondRenderer']);
