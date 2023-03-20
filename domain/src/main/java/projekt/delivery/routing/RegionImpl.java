package projekt.delivery.routing;

import org.jetbrains.annotations.Nullable;
import projekt.base.DistanceCalculator;
import projekt.base.EuclideanDistanceCalculator;
import projekt.base.Location;

import java.util.*;

import static org.tudalgo.algoutils.student.Student.crash;

class RegionImpl implements Region {

    private final Map<Location, NodeImpl> nodes = new HashMap<>();
    private final Map<Location, Map<Location, EdgeImpl>> edges = new HashMap<>();
    private final List<EdgeImpl> allEdges = new ArrayList<>();
    private final DistanceCalculator distanceCalculator;

    private final Collection<Node> unmodifiableNodes = Collections.unmodifiableCollection(nodes.values());
    private final Collection<Edge> unmodifiableEdges = Collections.unmodifiableCollection(allEdges);

    /**
     * Creates a new, empty {@link RegionImpl} instance using a {@link EuclideanDistanceCalculator}.
     */
    public RegionImpl() {
        this(new EuclideanDistanceCalculator());
    }

    /**
     * Creates a new, empty {@link RegionImpl} instance using the given {@link DistanceCalculator}.
     */
    public RegionImpl(DistanceCalculator distanceCalculator) {
        this.distanceCalculator = distanceCalculator;
    }

    /**
     Returns the node associated with the specified location, or null if no node is associated with the location.
     @param location the location of the node to retrieve
     @return the node associated with the location, or null if no node is associated with the location
     */
    @Override
    public @Nullable Node getNode(Location location) {
        return nodes.get(location);
    }

    @Override
    public @Nullable Edge getEdge(Location locationA, Location locationB) {
        if (locationA.compareTo(locationB) > 0) {
            Location temp = locationA;
            locationA = locationB;
            locationB = temp;
        }
        final @Nullable Map<Location, EdgeImpl> firstDim = edges.get(locationA);
        if (firstDim == null) {
            return null;
        }
        return firstDim.get(locationB);
    }

    @Override
    public Collection<Node> getNodes() {
        return unmodifiableNodes;
    }

    @Override
    public Collection<Edge> getEdges() {
        return unmodifiableEdges;
    }

    @Override
    public DistanceCalculator getDistanceCalculator() {
        return distanceCalculator;
    }

    /**
     * Adds the given {@link NodeImpl} to this {@link RegionImpl}.
     * @param node the {@link NodeImpl} to add.
     */
    void putNode(NodeImpl node) {
        if (node.getRegion() != this) {
            throw new IllegalArgumentException(String.format("Node %s has incorrect region", node));
        }
        nodes.put(node.getLocation(), node);
    }

    /**
     * Adds the given {@link EdgeImpl} to this {@link RegionImpl}.
     * @param edge the {@link EdgeImpl} to add.
     */
    void putEdge(EdgeImpl edge) {
        if (edge == null) {
            throw new IllegalArgumentException("Cannot add a null edge");
        }

        // Check if edge is in this region
        if (!this.equals(edge.getRegion())) {
            throw new IllegalArgumentException(String.format("Edge %s has incorrect region", edge));
        }

        Node nodeA = edge.getNodeA();
        Node nodeB = edge.getNodeB();

        if (nodeA == null && nodeB == null) {
            throw new IllegalArgumentException(String.format("Node{A,B} {%s, %s} is not part of the region", edge.getLocationA(), edge.getLocationB()));
        } else if (nodeA == null) {
            throw new IllegalArgumentException(String.format("NodeA %s is not part of the region", edge.getLocationA()));
        } else if (nodeB == null) {
            throw new IllegalArgumentException(String.format("NodeB %s is not part of the region", edge.getLocationB()));
        }

        // Swap nodes if locationB < locationA
        Location locationA = edge.getLocationA();
        Location locationB = edge.getLocationB();
        if (locationB.compareTo(locationA) < 0) {
            Location temp = locationA;
            locationA = locationB;
            locationB = temp;
            nodeA = edge.getNodeB();
            nodeB = edge.getNodeA();
        }

        Map<Location, EdgeImpl> innerMap = edges.getOrDefault(locationA, new HashMap<>());
        innerMap.put(locationB, edge);
        edges.put(locationA, innerMap);
        allEdges.add(edge);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof RegionImpl other)) {
            return false;
        }

        if (o == this) {
            return true;
        }
        return Objects.equals(this.nodes, other.nodes) && Objects.equals(this.edges, other.edges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodes, edges);
    }
}
